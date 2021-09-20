package com.ss.lms.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionsManager {
    private static Connection currentConnection;
    
    public static Connection getConnection() throws SQLException {
        if (currentConnection != null) { return currentConnection; }
        
        currentConnection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC",
            "root",
            "root");
        
        currentConnection.setAutoCommit(false);

        return currentConnection;
    }

    public static void closeConnection() {
        if (currentConnection == null) return;
        try {
            currentConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollbackConnection() {
        if (currentConnection == null) return;
        try {
            currentConnection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
