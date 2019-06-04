package com.magic.web.tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.magic.utils.EnvironmentPropertiesReader;
import com.magic.utils.SQLJDBC;

public class JDBCTestDemo {

	EnvironmentPropertiesReader objEnvironmentPropertiesReader;
	Connection connection = null;
	ResultSet resultSet = null;
	Properties prop;
	
	String JDBC_driver = "";
	String SQLServer = "";
	String hostName = "";
	String portNumber = "";
	String dataBaseName = "";
	String userId = "";
	String password = "";
	String selectStatement = "";
	String tableName = "";
	String selectQuery = "";
	String connectionNameString = "";
	String databasename="";
	String sqlUSESchema="";
	String useSchema="";
	String tableSchema="";
	
	
	@BeforeTest
	public void CreateSQLConnection()
	{
		objEnvironmentPropertiesReader=EnvironmentPropertiesReader.getInstance();
		prop=objEnvironmentPropertiesReader.PropertiesFile();
		JDBC_driver = prop.getProperty("JDBC_Driver");
		SQLServer = prop.getProperty("SQLServer");
		hostName = prop.getProperty("Host");
		portNumber = prop.getProperty("PortNumber");
		dataBaseName = prop.getProperty("DB");
		userId = prop.getProperty("User");
		password = prop.getProperty("Password");
		
		try {
			connectionNameString = SQLServer + hostName + portNumber + dataBaseName;
			connection = SQLJDBC.CreatingSQLConnection(JDBC_driver, connectionNameString, userId,
					password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	@Test(priority=0, enabled=false)
	public void ToFetchAllRecordsFromTable() {		
		try {
			
			selectStatement=prop.getProperty("Select");
			tableName=prop.getProperty("TableName");			
			databasename=prop.getProperty("DataBaseName");			
			sqlUSESchema=prop.getProperty("SQLUSESchema");
			
			useSchema=sqlUSESchema + " " + databasename;
			selectQuery = selectStatement + " " + tableName;
			resultSet = SQLJDBC.SelectQueries(selectQuery);

			// This will fectch the all details in table
			while (resultSet.next()) {
				// Retrieve by column name
				int companyName = resultSet.getInt("CustomerID");
				String customerName = resultSet.getString("CustomerName");
				String contactName = resultSet.getString("ContactName");
				String country = resultSet.getString("Country");
				// Display values
				System.out.println(companyName + " : : " + customerName + " : : " + contactName + country);
			}		
		} catch (Exception e) {
			
			try {
				throw e;
			} catch (Exception e1) {				
				e1.printStackTrace();
			}
		}
	}
	
	
	@Test(priority=1)
	public void ToFetchAllTableNameFromDB()
	{
		selectStatement=prop.getProperty("Select");
		tableName=prop.getProperty("TableName");
		tableSchema=prop.getProperty("TableSchema");
		String toGetAllTableNameUnderDabtase="";
		
		try {

			toGetAllTableNameUnderDabtase=selectStatement + " " + tableSchema;
			resultSet = SQLJDBC.SelectQueries(toGetAllTableNameUnderDabtase);
			
			System.out.println("Below are the table which is present under table " + tableName);
			while (resultSet.next()) {
				// Retrieve by column name			
				String tablename = resultSet.getString("name");
				
				// Display values
				System.out.println(tablename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@AfterTest
	public void CloseConnection()
	{
		try {			
			connection.close();

		} catch (Exception e) {
			
		}
	}

}
