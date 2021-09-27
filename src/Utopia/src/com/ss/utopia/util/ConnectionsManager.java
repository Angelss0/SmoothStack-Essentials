package com.ss.utopia.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionsManager {
    private static Connection currentConnection;

    private static String _db = null;
    public static void setDatabase(String db) { _db = db; }

    public static Connection getConnection() throws SQLException {
        if (currentConnection != null) { return currentConnection; }

        currentConnection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/" + _db + "?useSSL=false&serverTimezone=UTC",
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
