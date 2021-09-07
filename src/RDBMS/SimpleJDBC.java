// package com.smoothstack.week2.jdbc;
package RDBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleJDBC {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
		String user = "root";
		String password = "root";

		Connection conn = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT * FROM library.tbl_author where authorId=?";
			int authorId = 1;

			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setInt(1, authorId);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("author name is :: " + resultSet.getString("authorName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
