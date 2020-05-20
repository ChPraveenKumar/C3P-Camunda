package com.orion.camunda.C3PCamunda.common;

import org.apache.log4j.Logger;

public class LoggerUtil {

	/**
	 * Convenient method to get application logger.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getApplicationLogger(Class<?> clazz) {
		return Logger.getLogger("applicationLogger." + clazz.getName());
	}

}
