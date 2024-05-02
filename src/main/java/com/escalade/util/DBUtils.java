package com.escalade.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	public static void closeQuietly(Connection connection, ResultSet resultSet, Statement... statements) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {}
        }
        
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {}
        }
        
        for (Statement statement : statements) {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
        }
    }
}
