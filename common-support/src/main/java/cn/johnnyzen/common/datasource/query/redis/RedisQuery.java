package cn.johnnyzen.common.datasource.query.redis;

import cn.johnnyzen.common.datasource.connector.redis.RedisConnector;
import cn.johnnyzen.common.datasource.entity.QueryJobInfo;
import cn.johnnyzen.common.datasource.query.AbstractQuery;
import cn.johnnyzen.common.dto.page.PageRequest;
import cn.johnnyzen.common.dto.page.PageResponse;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import cn.johnnyzen.common.util.jinja.JinjaUtil;
import cn.johnnyzen.common.util.lang.CollectionUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.commands.ProtocolCommand;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/11/25 18:19
 * @description ...
 */
public class RedisQuery extends AbstractQuery<RedisConnector> {
    private final static Logger logger = LoggerFactory.getLogger(RedisQuery.class);

    /**
     * //TODO
     * sal_template := commands set
     * eg: "COMMAND;bdp:vhr:canLastData:vin:{{vin}};sg_{{standardSignalId}} & COMMAND;bdp:vhr:canLastData:vin:{{vin}};sg_{{standardSignalId}}"
     * => "HGET;bdp:vhr:canLastData:vin:LM8F7E696NA000070;sg_390 & HGET;bdp:vhr:canLastData:vin:LM8F7E696NA000435;sg_390";
     */
    private static final String DEFAULT_COMMANDS_SET_TEMPLATE_SEPARATOR_CHAR = "&";

    /**
     * //TODO 完善本字段的注释
     *
     * eg: "COMMAND;bdp:vhr:canLastData:vin:{{vin}};sg_{{standardSignalId}}"
     *  => "HGET;bdp:vhr:canLastData:vin:LM8F7E696NA000070;sg_390";
     */
    private static final String DEFAULT_COMMANDS_TEMPLATE_SEPARATOR_CHAR = ";";

    /**
     * 数据库(redis) 的字符集
     */
    private static final String DATABASE_CHARSET = "UTF-8";

    /**
     * 合法/被允许的操作类型
     */
    private static Set<ProtocolCommand> LEGAL_OPERATION = null;

    static {
        LEGAL_OPERATION = new HashSet<>();
        LEGAL_OPERATION.add(Protocol.Command.GET);
        LEGAL_OPERATION.add(Protocol.Command.GETRANGE);
        LEGAL_OPERATION.add(Protocol.Command.HGET);
        LEGAL_OPERATION.add(Protocol.Command.HGETALL);
        LEGAL_OPERATION.add(Protocol.Command.HKEYS);
        LEGAL_OPERATION.add(Protocol.Command.HEXISTS);
        LEGAL_OPERATION.add(Protocol.Command.HSCAN);
        LEGAL_OPERATION.add(Protocol.Command.LRANGE);
        LEGAL_OPERATION.add(Protocol.Command.XRANGE);
        LEGAL_OPERATION.add(Protocol.Command.ZRANGE);
    }

    public RedisQuery(RedisConnector builtConnector) {
        super(builtConnector);
    }


    /**
     *
     * @param queryJobInfo
     *  sqlTemplat format
     *      "COMMAND_TYPE;NOT_DATABASE_RESPONSE_DATA(非数据库的响应数据, json 格式);ARGS_1;ARGS_2;...;ARGS_N"
     *  sqlTemplate sample:
     *      HGETALL;{\"vin\":\"LM8F7E696NA000070\"};bdp:vhr:canLastData:vin:{{vin}}
     *  sql:
     *      HGETALL;{\"vin\":\"LM8F7E696NA000070\"};bdp:vhr:canLastData:vin:LM8F7E696NA000070
     * @param params
     * @return
     * @throws ApplicationRuntimeException
     * @throws SQLException
     */
    @Override
    public PageResponse query(QueryJobInfo queryJobInfo, Map<String, Object> params) throws ApplicationRuntimeException, SQLException {
        Map<String, Object> requestParams = (Map<String, Object>) params.get(PageRequest.PARAMS_NAME);
        String parsedSqlTemplate = JinjaUtil.jinjaConvert(queryJobInfo.getSqlTemplate(), requestParams);
//        RedisConnector connector = new RedisConnector(queryJobInfo.getDatasourceUrl(), queryJobInfo.getDatasourcePassword());
//        connector.build();
        Jedis connection = this.connector.getConnection();

        List<Map<String, Object>> responseList = new LinkedList<>();

        //step0 分解成 N 段 commands; 1 个 commands 即 1个独立的 Query SQL 任务
        List<String>  commandsList = resolveParsedSqlTemplateAsCommandsList(parsedSqlTemplate);
        for( String commandsString: commandsList){
            //step1 去除首尾空格
            commandsString = commandsString.trim();

            //step2 解析命令 eg: {"HGETALL", "{\"vin\":\"LM8F7E696NA000070\"}", "bdp:vhr:canLastData:vin:LM8F7E696NA000070";}
            String commands[] = CollectionUtil.clearEmptyChar(commandsString.split(DEFAULT_COMMANDS_TEMPLATE_SEPARATOR_CHAR));

            String commandTypeString = commands[0];
            if(StringUtils.isEmpty(commandTypeString)){
                throw new ApplicationRuntimeException("commandType must be not empty!");
            }
            // commandType: HGETALL / HGET / ...
            Protocol.Command commandType = Protocol.Command.valueOf(commandTypeString);
            //step2.1 过滤非法操作
            filteringIllegalOperation(commandType);

            // step2.2 解析非数据库响应的结果数据集
            Map<String, String> notDatabaseResponseData = parseNotDatabaseResponseData(commands[1]);

            // step2.3 结合 commandType, 向 redis 数据库发起的命令参数; 1: 摒弃第 1,2 个参数(1:commandType;2:NOT_DATABASE_RESPONSE_DATA)
            String [] commandArgs = Arrays.copyOfRange(commands, 2, commands.length);

            //step3 查询数据库数据
            long startTime = System.currentTimeMillis();
            Object databaseQueryResultSet = connection.sendCommand(commandType, commandArgs);
            long endTime = System.currentTimeMillis();
            logger.debug(String.format("Time-consuming: %dms [from database]", (endTime - startTime)));

            //step4 解析组装成当前行命令的响应结果数据集
            long startTime2 = System.currentTimeMillis();
            List<Map<String, Object>> resultSet =  parseDatabaseResultSet(notDatabaseResponseData, commandType, databaseQueryResultSet);
            long endTime2 = System.currentTimeMillis();
            logger.debug(String.format("Time-consuming: %dms [parse and packaging result set]", (endTime2 - startTime2)));
            responseList.addAll(resultSet);
        }

        //step5 封装响应对象: 不予分页
        PageResponse result = new PageResponse(1, Integer.MAX_VALUE, responseList, responseList.size());
        return result;
    }

    /**
     * 解析非数据库的响应数据
     * @description {"key1Name":"value1Name", "key2Name":"value2Name"}
     *  value: 必须为 String
     * @param notDatabaseResponseDataJsonStr
     * @return
     */
    public static Map<String, String> parseNotDatabaseResponseData(String notDatabaseResponseDataJsonStr){
        Map<String, String> notDatabaseResponseData = null;
        if(!StringUtils.isEmpty(notDatabaseResponseDataJsonStr)){
            notDatabaseResponseData = JSON.parseObject(notDatabaseResponseDataJsonStr.trim(), Map.class);
        }
        return notDatabaseResponseData;
    }

    /**
     * 将 初次渲染后的 SqlTemplate 解析成 N 段 独立的 command
     * @return
     */
    public static List<String> resolveParsedSqlTemplateAsCommandsList(String sqlTemplate){
        List<String> commandsList = new ArrayList<>();
        if(StringUtils.isEmpty(sqlTemplate)){
            logger.warn("sqlTemplate is empty!");
            return commandsList;
        }
        if(sqlTemplate.contains(DEFAULT_COMMANDS_SET_TEMPLATE_SEPARATOR_CHAR)){
            commandsList = Arrays.asList(sqlTemplate.split(DEFAULT_COMMANDS_SET_TEMPLATE_SEPARATOR_CHAR));
        } else {
            commandsList.add(sqlTemplate);
        }
        return commandsList;
    }

    /**
     * 过滤非法操作
     * @description 仅允许查询，不允许写、修改数据
     * @param commandType
     */
    public static void filteringIllegalOperation(ProtocolCommand commandType){
        boolean isLegalOperation = LEGAL_OPERATION.contains(commandType);
        if(!isLegalOperation){
            String errorMseeage = String.format("This command type(%s) is illegal operation!", commandType);
            logger.error(errorMseeage);
            throw new ApplicationRuntimeException(errorMseeage);
        }
    }

    protected static List<Map<String, Object>> parseDatabaseResultSet(Map<String, String> notDatabaseResponseData, Protocol.Command commandType, Object databaseResultSet){
        //TODO
        List<Map<String, Object>> responseList = new LinkedList<>();
        switch (commandType){
            case HGETALL:
                responseList = parseHGETALLDatabaseResultSet(notDatabaseResponseData, databaseResultSet);
                break;
            default:
                logger.warn("RedisDBQuery is not support this command type(%s) now!", commandType);
                break;
        }
        return responseList;
    }

    protected static List<Map<String, Object>> parseHGETALLDatabaseResultSet(Map<String, String> notDatabaseResponseData, Object databaseResultSet){

        List<Map<String, Object>> responseList = new LinkedList<>();
        List<Object> results = (ArrayList) databaseResultSet;
        if(results != null && results.size()>0){
            //logger.debug("fieldsContent: ");
            for(int i=0; i< results.size()/2; i++){
                Map<String, Object> record = null;
                record = parseHGETOneDBResult(i, results, notDatabaseResponseData);
                if(!ObjectUtils.isEmpty(record)){
                    responseList.add(record);
                }
            }
        }
        return responseList;
    }

    public static  Map<String, Object> parseHGETOneDBResult(int lineIndex, List<Object> results, Map<String, String> notDatabaseResponseData){
        String fieldKey = null;
        String fieldValue = null;
        Map<String, Object> record = null;
        try {
            fieldKey = new String(
                    Base64.getDecoder().decode( Base64.getEncoder().encodeToString( (byte[]) results.get(2*lineIndex) ) )
                    , DATABASE_CHARSET
            );
            //logger.debug("fieldKey: {}", fieldKey);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try {
            fieldValue = new String(
                    Base64.getDecoder().decode( Base64.getEncoder().encodeToString( (byte[]) results.get(2*lineIndex+1) ) )
                    , DATABASE_CHARSET
            );
            //logger.debug("fieldValue: {}", fieldValue);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        if( (!StringUtils.isEmpty(fieldKey) ) && (!StringUtils.isEmpty(fieldValue)) ){
            record= new HashMap<>();
            if(!CollectionUtil.isEmpty(notDatabaseResponseData)){
                record.putAll(notDatabaseResponseData);
            }
            record.put(fieldKey, fieldValue);

        }
        return record;
    }

    @Override
    public PageResponse autoPagingQuery(QueryJobInfo queryJobInfo, Map<String, Object> params) throws ApplicationRuntimeException {
        //TODO 是否支持自动分页?可不予支持
        throw new ApplicationRuntimeException("this auto paging query method is not support for redis database!");
    }
}
