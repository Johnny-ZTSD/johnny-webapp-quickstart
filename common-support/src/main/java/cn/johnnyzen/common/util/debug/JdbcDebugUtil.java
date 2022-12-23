package cn.johnnyzen.common.util.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/12 11:07
 * @description JDBC 连接的 调试工具
 */
public class JdbcConnectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(JdbcConnectionUtil.class);

    public static void printResultSet(ResultSet queryResultSet) throws SQLException {
        //结果集
//        ResultSet queryResultSet = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(queryResultSet == null){
            logger.debug("queryResultSet is empty!");
            return;
        }

        //结果集的元数据
        ResultSetMetaData rsmd = queryResultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Print.println(String.format("\ncolumns(%d):", columnCount));
        for (int i=1;i<=columnCount; i++){
            /** sample data
             [1] id(label:id / type:-5 / typeName:BIGINT UNSIGNED / className:java.math.BigInteger / displaySize:20)
             [2] signal_name(label:signal_name / type:12 / typeName:VARCHAR / className:java.lang.String / displaySize:100)
             [3] field_name(label:field_name / type:12 / typeName:VARCHAR / className:java.lang.String / displaySize:100)
             [4] check_status(label:check_status / type:-6 / typeName:TINYINT / className:java.lang.Integer / displaySize:3)
             */
            Print.println(
                String.format("[%d] %s(label:%s / type:%s / typeName:%s / className:%s / displaySize:%s) \t",
                    i,
                    rsmd.getColumnName(i),
                    rsmd.getColumnLabel(i),
                    rsmd.getColumnType(i),
                    rsmd.getColumnTypeName(i),
                    rsmd.getColumnClassName(i),
                    rsmd.getColumnDisplaySize(i)
                )
            );
        }

        //展示数据
        Print.println("query-result-data-list:");
        //总记录数统计
        int countLine = 0;
        while(queryResultSet.next()){
            /** sample-data
             1	_GW_PT__Active_Source	nm_gw_pt_active_source	1
             2	ac_acStatus	ac_ac_status	1
             3	ac_acStatusReq	ac_ac_status_req	1
             4	AC_Active_Source	ac_active_source	1
             5	ac_ActPtcOutWaterTempreture	ac_act_ptc_out_water_tempreture	1
             6	ac_ambientTemp	ac_ambient_temp	1
             7	ac_ambientTempValid	ac_ambient_temp_valid	1
             8	ac_autoStatus	ac_auto_status	1
             9	ac_blowerLvAUTOStatus	ac_blower_lv_auto_status	1
             10	ac_blowerLvStatus	ac_blower_lv_status	1
             */
            int columnIndex = 1;
            StringBuilder lineBuilder = new StringBuilder();
            for(int j = 1; j <= columnCount; j++){
                String columnName = rsmd.getColumnName(columnIndex);
                Object columnValue = queryResultSet.getObject(j);
                lineBuilder.append(String.format(" %s : %s | ", columnName, columnValue));
                columnIndex++;
            }
            logger.debug("line[{}]" + lineBuilder.toString(), countLine);
            countLine++;
        }
        Print.println(String.format("total-record = %d", countLine));
    }
}
