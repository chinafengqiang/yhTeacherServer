package com.study.utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcGetConnetion {
	public Connection getConn(){
		try{
			
			Class.forName("org.gjt.mm.mysql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lawstudycenter", "root", "");
			return conn;
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
}
