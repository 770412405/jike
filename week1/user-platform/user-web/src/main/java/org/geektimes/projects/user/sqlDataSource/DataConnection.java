package org.geektimes.projects.user.sqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: 肖震
 * @date: 2021/3/3
 * @since:
 */
public class DataConnection {
    private DataSource dataSource;
    private static final DataConnection INSTANCE = new DataConnection();

    public void setDataSource(DataSource dataSource) {
        DataConnection.INSTANCE.dataSource = dataSource;
    }

    public static DataConnection getInstance() {
        return INSTANCE;
    }


    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("连接数据库失败");
        }
    }
}
