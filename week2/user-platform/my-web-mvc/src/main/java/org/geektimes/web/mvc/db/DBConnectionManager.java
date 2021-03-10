package org.geektimes.web.mvc.db;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionManager { // JNDI Component

    private final Logger logger = Logger.getLogger(DBConnectionManager.class.getName());
    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
            "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "name VARCHAR(16) NOT NULL, " +
            "password VARCHAR(64) NOT NULL, " +
            "email VARCHAR(64) NOT NULL, " +
            "phoneNumber VARCHAR(64) NOT NULL" +
            ")";

    @Resource(name = "jdbc/UserPlatformDB")
    private DataSource dataSource;


    @PostConstruct
    public void init() {
        if (getConnection() != null) {
            logger.log(Level.INFO, "获取 JNDI 数据库连接成功！");
        }
        //初始化表
        initData();

    }

    private void initData()  {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            boolean createStatus=statement.execute(CREATE_USERS_TABLE_DDL_SQL);
        } catch (SQLException e) {
            //e.printStackTrace();
            logger.log(Level.INFO,"数据库表初始化失败,表已存在");
        }finally {
            if(statement!=null){
                try {
                    statement.close();
                }catch (SQLException e){
                    logger.log(Level.INFO,"关闭Statement失败");
                }
            }
        }
    }

    public Connection getConnection() {
        // 依赖查找
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return connection;
    }



}
