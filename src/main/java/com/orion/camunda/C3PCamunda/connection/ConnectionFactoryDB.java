package com.orion.camunda.C3PCamunda.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryDB {
	//static reference to itself
    private static ConnectionFactoryDB instance = new ConnectionFactoryDB();
    public static final String URL = "jdbc:mysql://tool-mysql-35913-development/requestinfo?zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true";
    public static final String USER = "root";
    public static final String PASSWORD = "root";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
    private static ConnectionFactoryDB instance1 = new ConnectionFactoryDB();
    public static final String URL_TEMPLATE_DB = "jdbc:mysql://tool-mysql-35913-development/Template_Schema?zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true&autoReconnect=true";

    //private constructor
    private ConnectionFactoryDB() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }   
    private Connection createConnectionToTemplateDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL_TEMPLATE_DB, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }   
     
    public static Connection getConnection() {
        return instance.createConnection();
    }
    public static Connection getConnectionToTemplateDB() {
        return instance1.createConnectionToTemplateDB();
    }
}
