package com.techm.c3p.workflow.camunda.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.techm.c3p.workflow.camunda.common.BpmLabels;
import com.techm.c3p.workflow.camunda.common.LoggerUtil;

public class ConnectionFactoryDB {
	//static reference to itself
    private static ConnectionFactoryDB instance = new ConnectionFactoryDB();
    private static final Logger logger = LoggerUtil.getApplicationLogger(BpmLabels.class);
   
    //private constructor
    private ConnectionFactoryDB() {
        try {
            Class.forName(BpmLabels.SQL_DRIVER_CLASS.getValue());
        } catch (ClassNotFoundException exe) {
            logger.error("ERROR: Unable to Create to Connection."+exe.getMessage());
        }
    }
     
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(BpmLabels.SQL_URL.getValue(), BpmLabels.SQL_USER.getValue(), BpmLabels.SQL_PASSWORD.getValue());
        } catch (SQLException exe) {
        	logger.error("ERROR: Unable to Connect to Database."+exe.getMessage());
        }
        return connection;
    }   
   
    public static Connection getConnection() {
        return instance.createConnection();
    }
   
}
