//package org.geektimes.projects.user.web.listener;
//
//import org.geektimes.projects.user.sqlDataSource.DataConnection;
//import org.geektimes.projects.user.sqlDataSource.SqlConstants;
//
//import javax.sql.DataSource;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
///**
// * @author: 肖震
// * @date: 2021/3/3
// * @since:
// */
//@WebListener
//public class DBDataSourceInitializerListener implements ServletContextListener {
//    private ServletContext servletContext;
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        servletContext = sce.getServletContext();
//        initDataSourceJNDI();
//        initData();
//    }
//
//    private void initData() {
//        Connection conn = null;
//        Statement statement = null;
//        try {
//            conn = DataConnection.getInstance().getConnection();
//            statement = conn.createStatement();
//            boolean createStatus = statement.execute(SqlConstants.CREATE_USERS_TABLE_DDL_SQL);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            servletContext.log("数据库表初始化失败");
//        } finally {
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    servletContext.log("关闭Statement失败");
//                }
//            }
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    servletContext.log("关闭Connection失败");
//                }
//            }
//        }
//    }
//
//    private void initDataSourceJNDI() {
//        try {
//            servletContext.log("JNDI加载dataSource");
//            Context context = new InitialContext();
//            DataSource dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/UserPlatformDB");
//            servletContext.log("JNDI加载dataSource and class:" + dataSource.getClass());
//            DataConnection.getInstance().setDataSource(dataSource);
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//
//    }
//}
