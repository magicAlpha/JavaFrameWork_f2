package com.magic.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLJDBC {

	// JDBC driver name and database URL
	// static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	// static String DB_URL =
	// "jdbc:sqlserver://10.11.2.121:1433;databaseName=PersonsDetails";
	
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet resultSet = null;

	public static Connection CreatingSQLConnection(String JDBC_driver, String connectionNameString, String userId,
			String passWord) {

		try {

			// STEP 2: Register JDBC driver
			Class.forName(JDBC_driver);

			// STEP 3: Open a connection
			connection = DriverManager.getConnection(connectionNameString, userId, passWord);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection == null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return connection;
	}
	
	// This method will fetch the select any existing database in the SQL schema using SQL USE statement
	public static ResultSet GetIntoDatadase(String useDatabaseName)
	{
		try {			
			statement=connection.createStatement();
			resultSet=statement.executeQuery(useDatabaseName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	// It will fetch the Select Queries
	public static ResultSet SelectQueries(String sqlQueries) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQueries);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement == null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // end try		
		return resultSet;
	}
}
