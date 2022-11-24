package cn.johnnyzen.common.util;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Johnny
 * @project JohnnyWebappQuickstart
 * @create-time 2022/11/24  23:20:22
 * @description 管理类 DBConnectionManager 支持对一个或多个由属性文件定义的数据库连接池的访问.客户程序可以调用 getInstance() 方法访问本类的唯一实例.
 * @reference-doc
 *  [1] 一个完备的数据库连接池类 - CSDN - https://blog.csdn.net/yangjiyue/article/details/1885486
 *  [2] 详解Java数据库连接池 - jb51 - https://www.jb51.net/article/214436.htm
 *  [3] JAVA 数据库连接池（伪代码，简单易读） - zoukankan - http://t.zoukankan.com/liqiu-p-3992783.html
 *  [4] Java 使用Redis连接池 - CSDN - https://blog.csdn.net/qq_27694835/article/details/115199011
 *  [5] JDBC连接池 （neo4j） - CSDN - https://blog.csdn.net/amanda_shuidi/article/details/84857810
 *  [6] java 对象池 commons-pool2 的使用(创建influxDB连接池) - jianshu - https://www.jianshu.com/p/46c27ee9e110 【推荐】
 *  [7] common-pool2的介绍和使用 - CSDN - https://blog.csdn.net/Tiffany_J/article/details/121488229
 *  [8] Apache Commons-pool2（整理） - jianshu - https://www.jianshu.com/p/b0189e01de35
 *  [9] 基于Commons-Pool2实现自己的redis连接池 - 京东云 - https://developer.jdcloud.com/article/2097 【推荐】
 */

public class DBConnectionManager {
    static private DBConnectionManager instance;  //  唯一实例
    static private int clients;

    private Vector drivers = new Vector();
    private PrintWriter log;
    private Hashtable pools = new Hashtable();

    /**
     * 返回唯一实例.如果是第一次调用此方法,则创建实例
     *
     * @return DBConnectionManager 唯一实例
     */
    static synchronized public DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        clients++;
        return instance;
    }

    /**
     * 建构函数私有以防止其它对象创建本类实例
     */
    private DBConnectionManager() {
        init();
    }

    /**
     * 将连接对象返回给由名字指定的连接池
     *
     * @param name 在属性文件中定义的连接池名字
     * @param con  连接对象
     */
    public void freeConnection(String name, Connection con) {
        DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        if (pool != null) {
            pool.freeConnection(con);
        }
    }

    /**
     * 获得一个可用的(空闲的)连接.如果没有可用连接,且已有连接数小于最大连接数
     * 限制,则创建并返回新连接
     *
     * @param name 在属性文件中定义的连接池名字
     * @return Connection 可用连接或null
     */
    public Connection getConnection(String name) {
        DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        if (pool != null) {
            return pool.getConnection();
        }
        return null;
    }

    /**
     * 获得一个可用连接.若没有可用连接,且已有连接数小于最大连接数限制,
     * 则创建并返回新连接.否则,在指定的时间内等待其它线程释放连接.
     *
     * @param name 连接池名字
     * @param time 以毫秒计的等待时间
     * @return Connection 可用连接或null
     */
    public Connection getConnection(String name, long time) {
        DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        if (pool != null) {
            return pool.getConnection(time);
        }
        return null;
    }

    /**
     * 关闭所有连接,撤销驱动程序的注册
     */
    public synchronized void release() {
        //  等待直到最后一个客户程序调用
        if (--clients != 0) {
            return;
        }

        Enumeration allPools = pools.elements();
        while (allPools.hasMoreElements()) {
            DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
            pool.release();
        }
        Enumeration allDrivers = drivers.elements();
        while (allDrivers.hasMoreElements()) {
            Driver driver = (Driver) allDrivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log(" 撤销 JDBC驱动程序  " + driver.getClass().getName() + " 的注册 ");
            } catch (SQLException e) {
                log(e, " 无法撤销下列JDBC驱动程序的注册:  " + driver.getClass().getName());
            }
        }
    }

    /**
     * 根据指定属性创建连接池实例.
     *
     * @param props 连接池属性
     */
    private void createPools(Properties props) {
        Enumeration propNames = props.propertyNames();
        while (propNames.hasMoreElements()) {
            String name = (String) propNames.nextElement();
            if (name.endsWith(" .url ")) {
                String poolName = name.substring(0, name.lastIndexOf(" . "));
                String url = props.getProperty(poolName + " .url ");
                if (url == null) {
                    log(" 没有为连接池 " + poolName + " 指定URL ");
                    continue;
                }
                String user = props.getProperty(poolName + " .user ");
                String password = props.getProperty(poolName + " .password ");
                String maxconn = props.getProperty(poolName + " .maxconn ", " 0 ");
                int max;
                try {
                    max = Integer.valueOf(maxconn).intValue();
                } catch (NumberFormatException e) {
                    log(" 错误的最大连接数限制:  " + maxconn + "  .连接池:  " + poolName);
                    max = 0;
                }
                DBConnectionPool pool =
                        new DBConnectionPool(poolName, url, user, password, max);
                pools.put(poolName, pool);
                log(" 成功创建连接池 " + poolName);
            }
        }
    }

    /**
     * 读取属性完成初始化
     */
    private void init() {
        InputStream is = getClass().getResourceAsStream("/META-INF/db.properties");
        Properties dbProps = new Properties();
        try {
            dbProps.load(is);
        } catch (Exception e) {
            System.err.println(" 不能读取属性文件, 请确保 db.properties 在CLASSPATH指定的路径中 ");
            return;
        }
        String logFile = dbProps.getProperty("logfile", "DBConnectionManager.log");
        try {
            log = new PrintWriter(new FileWriter(logFile, true), true);
        } catch (IOException e) {
            System.err.println(" 无法打开日志文件:  " + logFile);
            log = new PrintWriter(System.err);
        }
        loadDrivers(dbProps);
        createPools(dbProps);
    }

    /**
     * 装载和注册所有JDBC驱动程序
     *
     * @param props 属性
     */
    private void loadDrivers(Properties props) {
        String driverClasses = props.getProperty("drivers");
        StringTokenizer st = new StringTokenizer(driverClasses);
        while (st.hasMoreElements()) {
            String driverClassName = st.nextToken().trim();
            try {
                Driver driver = (Driver)
                        Class.forName(driverClassName).newInstance();
                DriverManager.registerDriver(driver);
                drivers.addElement(driver);
                log(" 成功注册JDBC驱动程序 " + driverClassName);
            } catch (Exception e) {
                log(" 无法注册JDBC驱动程序:  " +
                        driverClassName + " , 错误:  " + e);
            }
        }
    }

    /**
     * 将文本信息写入日志文件
     */
    private void log(String msg) {
        log.println(new Date() + " :  " + msg);
    }

    /**
     * 将文本信息与异常写入日志文件
     */
    private void log(Throwable e, String msg) {
        log.println(new Date() + " :  " + msg);
        e.printStackTrace(log);
    }

    /**
     * 此内部类定义了一个连接池.它能够根据要求创建新连接,直到预定的最
     * 大连接数为止.在返回连接给客户程序之前,它能够验证连接的有效性.
     */
    class DBConnectionPool {
        /**
         * 连接池的名称
         */
        private String name;

        private String URL;
        private String user;
        private String password;

        private int checkedOut;
        private Vector freeConnections = new Vector();
        private int maxActive;

        /* *
         * 创建新的连接池
         *
         * @param name 连接池名字
         * @param URL 数据库的JDBC URL
         * @param user 数据库帐号,或 null
         * @param password 密码,或 null
         * @param maxActive 此连接池允许建立的最大连接数
         */
        public DBConnectionPool(String name, String URL, String user, String password, int maxActive) {
            this.name = name;
            this.URL = URL;
            this.user = user;
            this.password = password;
            this.maxActive = maxActive;
        }

        /**
         * 将不再使用的连接返回给连接池
         *
         * @param con 客户程序释放的连接
         */
        public synchronized void freeConnection(Connection con) {
            // 将指定连接加入到向量末尾
            freeConnections.addElement(con);
            checkedOut--;
            notifyAll();
        }

        /**
         * 从连接池获得一个可用连接.
         * 如没有空闲的连接且当前连接数小于最大连接数限制,则创建新连接;
         * 如原来登记为可用的连接不再有效,则从向量删除之,然后递归调用自己以尝试新的可用连接.
         */
        public synchronized Connection getConnection() {
            Connection con = null;
            if (freeConnections.size() > 0) {
                //  获取向量中第一个可用连接
                con = (Connection) freeConnections.firstElement();
                freeConnections.removeElementAt(0);
                try {
                    if (con.isClosed()) {
                        log(" 从连接池 " + name + " 删除一个无效连接 ");
                        //  递归调用自己,尝试再次获取可用连接
                        con = getConnection();
                    }
                } catch (SQLException e) {
                    log(" 从连接池 " + name + " 删除一个无效连接 ");
                    //  递归调用自己,尝试再次获取可用连接
                    con = getConnection();
                }
            } else if (maxActive == 0 || checkedOut < maxActive) {
                con = newConnection();
            }
            if (con != null) {
                checkedOut++;
            }
            return con;
        }

        /**
         * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间
         * 参见前一个getConnection()方法.
         *
         * @param timeout 以毫秒计的等待时间限制
         */
        public synchronized Connection getConnection(long timeout) {
            long startTime = Calendar.getInstance().getTimeInMillis();
            Connection con;
            while ((con = getConnection()) == null) {
                try {
                    wait(timeout);//线程等待指定的时间
                } catch (InterruptedException e) {
                }
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if ((currentTime - startTime) >= timeout) {
                    //  wait()返回的原因是超时
                    return null;
                }
            }
            return con;
        }

        /**
         * 关闭所有连接
         */
        public synchronized void release() {
            Enumeration allConnections = freeConnections.elements();
            while (allConnections.hasMoreElements()) {
                Connection con = (Connection) allConnections.nextElement();
                try {
                    con.close();
                    log(" 关闭连接池 " + name + " 中的一个连接 ");
                } catch (SQLException e) {
                    log(e, " 无法关闭连接池 " + name + " 中的连接 ");
                }
            }
            freeConnections.removeAllElements();
        }

        /**
         * 创建新的连接
         */
        private Connection newConnection() {
            Connection con = null;
            try {
                if (user == null) {
                    con = DriverManager.getConnection(URL);
                }
            } catch (Exception e) {
                log(" 无法取得新连接 ");
            }
            return con;
        }
    }
}
