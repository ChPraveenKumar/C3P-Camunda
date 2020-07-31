package com.techm.orion.camunda.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.techm.orion.camunda.connection.ConnectionFactoryDB;

public class DBUrlSingleton {

	private static final Logger logger = LoggerUtil.getApplicationLogger(DBUrlSingleton.class);

	// static variable instance of type DBUrlSingleton
	private static DBUrlSingleton instance = null;
	private String endpointUrl;
	private Connection connection;
	private Statement statement;

	// private constructor restricted to this class itself
	private DBUrlSingleton() {
		connection = ConnectionFactoryDB.getConnection();
		String query = "select endpoint_value from endpointurls where endpoint_name = 'c3p_java_url'";
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				setEndpointUrl(rs.getString("endpoint_value"));
				logger.info("Endpoint value for C3P java is " + getEndpointUrl());
			}
		} catch (SQLException exe) {
			logger.error("ERROR: Unable to Create connection." + exe.getMessage());
		} finally {
			close(rs);
			close(statement);
			close(connection);
		}

	}

	// static method to create instance of Singleton class
	public static DBUrlSingleton getInstance() {
		if (instance == null) {
			instance = new DBUrlSingleton();
			logger.info("Instance created for DBUrlSingleton class to fetch Endpoint URL");
		}

		return instance;
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

	public void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException exe) {
				logger.error("ERROR: Unable to Close to connection." + exe.getMessage());
			}
		}
	}

	public void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException exe) {
				logger.error("ERROR: Unable to Close to statement." + exe.getMessage());
			}
		}
	}

	public void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException exe) {
				logger.error("ERROR: Unable to Close to resultSet." + exe.getMessage());
			}
		}
	}
}
