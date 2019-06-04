package com.magic.utils;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class FilloUtil {

	static String path="C:\\Users\\harishchandra.yadav\\Desktop\\CourseTest.xlsx";
	
	public static void main(String args[]) throws Exception{
		//String strQuery="Select * from Sheet1 where SR=1";
		//String strQuery="INSERT INTO Sheet1(SR,Name,Course) VALUES(6,'Parker','Jython')";
		//String strQuery="Update Sheet1 Set Country='US' where ID=100 and name='John'";
		//String delete="delete from Sheet1 where SR=6";
		//InsertOrUpdateData(path, delete);
		GetDataHashMap(path);
	}
	
	
	public static String GetData(String path,String strQuery,String fieldName) throws Exception{
		String value=null;
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(path);
		Recordset recordset=connection.executeQuery(strQuery);
		while(recordset.next()){
	    value=recordset.getField(fieldName);
		System.out.println(value);
		}
		recordset.close();
		connection.close();
		return value;
	}
	
	public static void InsertOrUpdateData(String path,String strQuery) throws Exception{
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(path);
		connection.executeUpdate(strQuery);
		connection.close();
	}
	
	
	public static void GetDataHashMap(String path) throws FilloException{
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(path);
		String strQuery1="Select * from Sheet2";
		connection.executeUpdate(strQuery1);
		
		connection.close();
	}
	
	
	
	
}
