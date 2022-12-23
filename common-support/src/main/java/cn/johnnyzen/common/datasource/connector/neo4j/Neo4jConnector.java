package cn.johnnyzen.common.datasource.connector.neo4j;

import org.neo4j.driver.*;
import org.neo4j.driver.async.AsyncSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.neo4j.driver.Values.parameters;

import java.sql.Connection;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/10/2 17:34
 * @description 1个 Connector 对象 即 一个产生一个 connection 连接实例
 * @reference-doc
 *  [1] Neo4j导入数据的5种方式详解配图 - CSDN - https://blog.csdn.net/qianmojl/article/details/116207175
 *  [2] Neo4j Native Java API示例 - vue5.com - http://www.vue5.com/neo4j/neo4j_native_java_api_example.html
 */
public class Neo4jConnector implements AutoCloseable {//TODO 待实现和适配 AbstractConnector 类
    private static Logger logger = LoggerFactory.getLogger(Neo4jConnector.class);

    private String url;

    private String username;

    private String password;

    private String database;

    private Connection connection = null;

    private Driver driver;

    /**
     * 构造器
     * @param url
     *  eg: bolt://localhost:7678
     * @param username
     *  eg: neo4j
     * @param password
     *  eg: neo4j
     */
    public Neo4jConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = GraphDatabase.driver(this.url, AuthTokens.basic(this.username, this.password));
    }

    public synchronized Session getSession(){
        return this.driver.session();
    }

    public synchronized Session getSession(SessionConfig sessionConfig){
        return this.driver.session(sessionConfig);
    }

    public synchronized AsyncSession getAsyncSession(){
        return this.driver.asyncSession();
    }

    public synchronized AsyncSession getAsyncSession(SessionConfig sessionConfig){
        return this.driver.asyncSession(sessionConfig);
    }

    public synchronized Driver getDriver(){
        return this.driver;
    }

    /**
     * 创建一个节点
     * @param nodeClass 节点类型
     * @param propertiesNames 新增节点的属性名列表
     * @param propertiesValues 新增节点的属性值列表
     * @reference-doc
     *  [1] Neo4j Java Driver (Slim package) 4.4 API - neo4j - https://neo4j.com/docs/api/java-driver/
     *  [2] The Neo4j Java Driver Manual v4.4 - neo4j - https://neo4j.com/docs/java-manual/current/
     */
    public Result addNode(String nodeClass, String [] propertiesNames,  Object [] propertiesValues) {
        Result result = null;
        if(propertiesNames == null || propertiesValues == null){
            throw new RuntimeException("`propertiesNames` and `propertiesValues` must be not null!");
        }
        if(propertiesNames.length != propertiesValues.length){
            throw new RuntimeException(String.format(
                    "`propertiesNames`(length:%d) and `propertiesValues`(length:%d) must be equal!",
                    propertiesNames.length,
                    propertiesValues.length)
            );
        }
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping a Cypher Query in a Managed Transaction provides atomicity
            // and makes handling errors much easier.
            // Use `session.writeTransaction` for writes and `session.readTransaction` for reading data.
            // These methods are also able to handle connection problems and transient errors using an automatic retry mechanism.

            // mergeScript(eg): "MERGE (a:Person {name: $x})"
            StringBuilder mergeScript = new StringBuilder();
            // parameters(eg): parameters("x", name);
            Object[] parameters = new Object[propertiesNames.length*2];

            mergeScript.append("MERGE (node:" + nodeClass + " {");
            for(int i=1;i<=propertiesNames.length;i++){
                String propertyName = propertiesNames[i-1];
                Object propertyValue = propertiesValues[i-1];
                mergeScript.append( propertyName + ":" + "$" + propertyName);
                if(i != propertiesNames.length){
                    mergeScript.append( ",");
                }

                parameters[i-1] = "$" + propertyName;
                parameters[i-1+propertiesNames.length] = propertyValue;

                logger.debug("{}#parameters[{}] : {}#parameters[{}]", "$" + propertyName, (i-1), propertyValue, (i-1+propertiesNames.length));
            }
            mergeScript.append("})");

            logger.debug("mergeScript: {}", mergeScript.toString());
            result = session.writeTransaction(tx -> tx.run(mergeScript.toString(), parameters("id", "123ABC","b", "3453", "c", "34535")));
//            result = session.writeTransaction(new TransactionWork<String>() {
//                @Override
//                public String execute(Transaction tx) {
//                    Result result = tx.run("CREATE (a:Greeting) " + "SET a.message = $message "
//                            + "RETURN a.message + ', from node ' + id(a)", parameters("message", message));
//                    return result.single().get(0).asString();
//                }
//            });
            session.close();
        }
        return result;
    }

    /**
     * 查询节点
     * @param queryScript
     *  eg: "MATCH (a:Person) WHERE a.name STARTS WITH $x RETURN a.name AS name"
     * @param propertiesNames
     *  eg: ["x"]
     * @param propertiesValues
     *  eg: ["valueX"]
     * @return recordsSet : Result extends Iterator<Record>
     *  // Each Cypher execution returns a stream of records.
     *  while (recordsSet.hasNext()) {
     *      Record record = recordsSet.next();
     *      // Values can be extracted from a record by index or name.
     *      System.out.println(record.get("name").asString());
     *  }
     * @referecne-doc
     *  [1] Neo4j Java Driver (Slim package) 4.4 API - Neo4j - https://neo4j.com/docs/api/java-driver/current/
     */
    public <V> Result queryNodes(String queryScript, String [] propertiesNames,  Object [] propertiesValues){
        Result recordsSet = null;
        try (Session session = driver.session()) {
            // A Managed transaction is a quick and easy way to wrap a Cypher Query.
            // The `session.run` method will run the specified Query.
            // This simpler method does not use any automatic retry mechanism.

            // parameters(eg): parameters("x", name);
            Object[] parameters = new Object[propertiesNames.length*2];
            for(int i=1;i<=propertiesNames.length;i++){
                String propertyName = propertiesNames[i-1];
                Object propertyValue = propertiesValues[i-1];

                parameters[i-1] = propertyName;
                parameters[i-1+propertiesNames.length] = propertyValue;

                logger.debug("{}#parameters[{}] : {}#parameters[{}]", "$" + propertyName, (i-1), propertyValue, (i-1+propertiesNames.length));
            }
            recordsSet = session.run( queryScript, parameters(parameters));
        }
        return recordsSet;
    }

    @Override
    public void close() {
        this.driver.close();
    }
}
