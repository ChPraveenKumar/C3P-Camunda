package com.orion.camunda.C3PCamunda.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.orion.camunda.C3PCamunda.connection.ConnectionFactoryDB;
import com.orion.camunda.C3PCamunda.connection.DBUtil;

public class DBUrlSingleton {
	
	private static final Logger logger = LoggerUtil
			.getApplicationLogger(DBUrlSingleton.class);
	
	// static variable single_instance of type DBUrlSingleton
    private static DBUrlSingleton single_instance = null;
 
    // variable of type String
    public String s;
    
    private Connection connection;
	Statement statement;
 
    // private constructor restricted to this class itself
    private DBUrlSingleton()
    {
        connection = ConnectionFactoryDB.getConnection();
		String query = "select endpoint_value from endpointurls where endpoint_name = 'c3p_java_url'";
		
		ResultSet rs = null;
		try {
			
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				s = rs.getString("endpoint_value");
				logger.info("Endpoint value for C3P java is "+s);
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(statement);
			DBUtil.close(connection);
		}
		
    }
 
    // static method to create instance of Singleton class
    public static DBUrlSingleton getInstance()
    {
        if (single_instance == null){
            single_instance = new DBUrlSingleton();
            
            logger.info("Instance created for DBUrlSingleton class to fetch Endpoint URL");
        }  
        
        return single_instance;
    }
}
