
package com.codequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DB {

    private DB() {
    }

    public static Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/codequest_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        String user = "root";

        String password = "lokesh29@06";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }

        return DriverManager.getConnection(url, user, password);
    }
}
