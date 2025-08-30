package com.litmus7.inventoryfeedv1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseConnector {
	private static String url;
	private static String user;
	private static String password;
	Connection con;
	
	static {
		Properties props=new Properties();
		try(FileReader reader=new FileReader("D:/ThreadAssignment/InventoryFeedV1/src/resources/config.properties")) {
			props.load(reader);
			url=props.getProperty("db.url");
			user = props.getProperty("db.username");
            password = props.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection()throws SQLException{
		return DriverManager.getConnection(url,user,password);
	}
}
