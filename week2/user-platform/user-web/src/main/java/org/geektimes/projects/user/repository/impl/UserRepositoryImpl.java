//package org.geektimes.projects.user.repository.impl;
//
//import org.geektimes.projects.user.domain.User;
//import org.geektimes.projects.user.repository.UserRepository;
//import org.geektimes.projects.user.sqlDataSource.DataConnection;
//import org.geektimes.projects.user.sqlDataSource.SqlConstants;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Collection;
//
///**
// * @author: 肖震
// * @date: 2021/3/3
// * @since:
// */
//public class UserRepositoryImpl implements UserRepository {
//
//    @Override
//    public boolean save(User user) {
//        Connection conn = null;
//        PreparedStatement statement = null;
//        try {
//            conn = DataConnection.getInstance().getConnection();
//            statement = conn.prepareStatement(SqlConstants.INSERT_USER_DML_SQL);
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getPassword());
//            statement.setString(3, user.getEmail());
//            statement.setString(4, user.getPhoneNumber());
//            int row = statement.executeUpdate();
//            if (row > 0) {
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean deleteById(Long userId) {
//        return false;
//    }
//
//    @Override
//    public boolean update(User user) {
//        return false;
//    }
//
//    @Override
//    public User getById(Long userId) {
//        return null;
//    }
//
//    @Override
//    public User getByNameAndPassword(String userName, String password) {
//        return null;
//    }
//
//    @Override
//    public User getByName(String name) {
//        return null;
//    }
//
//    @Override
//    public Collection<User> getAll() {
//        return null;
//    }
//}
