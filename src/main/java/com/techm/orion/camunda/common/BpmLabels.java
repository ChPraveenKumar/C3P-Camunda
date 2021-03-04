package com.techm.orion.camunda.common;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author Anjireddy Reddem
 *
 */
public enum BpmLabels {
	/* Sql properties */
	SQL_URL, SQL_USER, SQL_PASSWORD, SQL_DRIVER_CLASS, SQL_TEMPLATE_DB_URL,
	EXTERNAL_SERVICE;

	private static final Logger logger = LoggerUtil.getApplicationLogger(BpmLabels.class);
	private static ResourceBundle resourceLabels;
	private String label;
	private static String bpmProperties = "bpm";

	/**
	 * This method is used to get the Value in String format
	 * 
	 * @return
	 */
	public String getValue() {
		if (label == null) {
			loadBpmLabels();
		}
		return label;
	}

	/**
	 * This method is used to load the Bpm label from the properties file and store
	 * the label values
	 */
	private void loadBpmLabels() {
		if (resourceLabels == null) {
			loadProperties();
		}
		label = resourceLabels.getString(this.toString());
	}

	private static void loadProperties() {
		resourceLabels = ResourceBundle.getBundle(bpmProperties);
		logger.info("BpmLabels - loadBpmLabels -> " + resourceLabels);
	}
}
