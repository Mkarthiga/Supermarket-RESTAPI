package com.supermarkets.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
@Component
public class DBConnection {
	private String url;
	private String username;
	private String password;
	public DBConnection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.url = "jdbc:mysql://localhost:3306/market";
		this.username = "root";
		this.password = "";
	}
	public Connection getConnection() throws SQLException {
		 return DriverManager.getConnection(url, username, password);
	}

	public void closeConnection(Connection connection) throws SQLException {
		 if (connection != null) {
		          connection.close();
		      }
		  }

}