package org.geektimes.projects.user.sqlDataSource;

/**
 * @author: 肖震
 * @date: 2021/3/3
 * @since:
 */
public class SqlConstants {

    public static final String DROP_USERS_TABLE_DDL_SQL = "DROP TABLE users if exists";

    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
            "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "name VARCHAR(16) NOT NULL, " +
            "password VARCHAR(64) NOT NULL, " +
            "email VARCHAR(64) NOT NULL, " +
            "phoneNumber VARCHAR(64) NOT NULL" +
            ")";

    public static final String INSERT_USER_DML_SQL =
            "INSERT INTO USERS(name,password,email,phoneNumber) VALUES (?,?,?,?)";

    public static final String QUERY_ALL_USERS_DML_SQL = "SELECT id,name,password,email,phoneNumber FROM users";
}
