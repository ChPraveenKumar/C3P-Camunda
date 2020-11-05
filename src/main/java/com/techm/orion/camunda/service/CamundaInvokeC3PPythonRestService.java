package com.techm.orion.camunda.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.techm.orion.camunda.common.DBPythonUrlSingleton;
import com.techm.orion.camunda.common.DBUrlSingleton;
import com.techm.orion.camunda.common.LoggerUtil;

public class CamundaInvokeC3PPythonRestService {
	private static String endpointUrl = DBPythonUrlSingleton.getInstance().getEndpointUrl();
	private static final Logger logger = LoggerUtil.getApplicationLogger(CamundaInvokeC3PPythonRestService.class);

	private static final String PERFORM_DECOMPOSITION = endpointUrl
			+ "/C3P/api/ResourceFunction/v4/decomp/";
	private static final String FIND_NEXT_PRIORITY_REQUEST = endpointUrl
			+ "/C3P/api/ResourceFunction/v4/findNextPriorityReq/";

	private static final String NOTIFICATION_API = endpointUrl
			+ "/C3P/api/ResourceFunction/v4/notification/";
	
	public String decomposeSo(String businessKey, String version) {
		logger.info("Inside decompose method url :"+PERFORM_DECOMPOSITION);
		
		return executeBpmProcess(PERFORM_DECOMPOSITION, businessKey, version);
	}

	public String nextRun(String businessKey, String version) {
		logger.info("Inside decompose method url :"+PERFORM_DECOMPOSITION);
		
		return executeBpmProcess(FIND_NEXT_PRIORITY_REQUEST, businessKey, version);
	}
	public String notificationAPI(String businessKey, String version) {
		logger.info("Inside decompose method url :"+PERFORM_DECOMPOSITION);
		
		return executeBpmProcess(NOTIFICATION_API, businessKey, version);
	}


	private HttpURLConnection openHttpConnection(String endpointUrl) {
		HttpURLConnection httpConnection = null;
		try {
			URL url = new URL(endpointUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setConnectTimeout(5000);
			httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			httpConnection.setRequestMethod("POST");
		} catch (MalformedURLException urlExe) {
			logger.error("URL Exception ->" + urlExe.getMessage());
		} catch (ProtocolException proExe) {
			logger.error("Protocol Exception ->" + proExe.getMessage());
		} catch (IOException ioExe) {
			logger.error("IO Exception ->" + ioExe.getMessage());
		}

		return httpConnection;
	}

	private void writeOutputStream(HttpURLConnection httpConnection, JSONObject jsonObject) {
		try (OutputStream outputStream = httpConnection.getOutputStream()) {
			outputStream.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException ioExe) {
			logger.error("writeOutputStream - IO Exception ->" + ioExe.getMessage());
		}
	}

	private String getInputStream(HttpURLConnection httpConnection) {
		String result = null;
		try (InputStream inputStream = new BufferedInputStream(httpConnection.getInputStream())) {
			result = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
			logger.info("getInputStream - " + result);
		} catch (IOException ioExe) {
			logger.error("getInputStream - IO Exception ->" + ioExe.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getJsonObject(String businessKey, String version, String requestType) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("SO_number", businessKey);
		jsonObject.put("version", version);
		logger.error("JSON Input" + jsonObject.toJSONString());
		if (requestType != null) {
			jsonObject.put("requestType", requestType);
		}
		return jsonObject;
	}

	private String jsonOutput(HttpURLConnection httpConnection) {
		String output = null;
		String input = null;
		try {
			JSONParser parser = new JSONParser();
			input = getInputStream(httpConnection);
			if(input !=null && input.trim().length()>0) {
				JSONObject json = (JSONObject) parser.parse(input);
				output = json.get("workflow_status").toString();
			}
		} catch (ParseException exe) {
			logger.error("Json Parse Exception ->" + exe.getMessage());
		}

		logger.info("JSON Output after processing the HttpURLConnection With endpoint URL ->" + output);
		return output;
	}

	private String executeBpmProcess(String endpointUrl, String businessKey, String version) {
		return executeBpmProcess(endpointUrl, businessKey, version, null);
	}

	private String executeBpmProcess(String endpointUrl, String businessKey, String version, String requestType) {
		String outputVar = null;
		logger.info("endpointUrl " + endpointUrl);
		logger.info("businessKey " + businessKey);
		logger.info("version " + version);
		getJsonObject(businessKey, version, requestType);
		HttpURLConnection httpConnection = openHttpConnection(endpointUrl);
		if (httpConnection != null) {
			writeOutputStream(httpConnection, getJsonObject(businessKey, version, requestType));
			outputVar = jsonOutput(httpConnection);
		}
		logger.info("outputVar " + outputVar);
		return outputVar;
	}

}
