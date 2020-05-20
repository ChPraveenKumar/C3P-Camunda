package com.orion.camunda.C3PCamunda.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.orion.camunda.C3PCamunda.common.DBUrlSingleton;
import com.orion.camunda.C3PCamunda.common.LoggerUtil;


public class CamundaInvokeC3PRestService {	
	
	private static final Logger logger = LoggerUtil
			.getApplicationLogger(CamundaInvokeC3PRestService.class);
	
	DBUrlSingleton dus = DBUrlSingleton.getInstance();
	
	@SuppressWarnings("unchecked")
	public String checkRequestType(String businessKey, String version,String requestType) throws ParseException, IOException{		
		String endURL = dus.s+"/C3P/DeviceReachabilityAndPreValidationTest/performReachabiltyTest";
		//String endURL = "http://localhost:8024"+"/C3P/DeviceReachabilityAndPreValidationTest/performReachabiltyTest";
		String outputVar = "false";
        try{
        	logger.info("Inside CheckRequestType method");
        	logger.info("Request type"+requestType);

        	if(requestType.equalsIgnoreCase("IOSUPGRADE"))
        	{
        		outputVar="true";
        	}
        	
        }
        catch(Exception e){
        	logger.error("Error in CheckRequestType method is ", e);
        }
		return outputVar;
	}
	
	@SuppressWarnings("unchecked")
	public String checkDeviceReachability(String businessKey, String version) throws ParseException, IOException{		
		String endURL = dus.s+"/C3P/DeviceReachabilityAndPreValidationTest/performReachabiltyTest";
		//String endURL = "http://localhost:8024"+"/DeviceReachabilityAndPreValidationTest/performReachabiltyTest";
		String outputVar = null;
        try{
        	logger.info("Inside checkDeviceReachability method");
        	logger.info("RequestID "+businessKey);
        	logger.info("Type "+businessKey.substring(0, 1));


		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
		    
		   
	        URL url = new URL(endURL);
		  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        logger.info("Output of checkDeviceReachability method is " + result);

	        JSONParser parser = new JSONParser();
	        JSONObject json = (JSONObject) parser.parse(result);
	        outputVar = json.get("output").toString();
	        logger.info("Output of checkDeviceReachability method is " + outputVar);
	        
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in checkDeviceReachability method is ", e);
        }
		return outputVar;
	}
	
	@SuppressWarnings("unchecked")
	public String deliverConfiguration(String businessKey, String version,Object requestType) throws ParseException, IOException{
		//String endURL = dus.s+"/C3P/DeliverConfigurationAndBackupTest/deliverConfigurationTest";
		//String endURL = "http://localhost:8024"+"/DeliverConfigurationAndBackupTest/deliverConfigurationTest";
		String endURL=dus.s+"/C3P/DeliverConfigurationAndBackupTest/deliverConfigurationTest";
		
		
		
		String outputVar = null;
        try{
        	logger.info("Inside deliverConfiguration method");
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
		    if(businessKey.substring(0, Math.min(businessKey.length(), 4)).equalsIgnoreCase("SLGC"))
		    	{
		    		requestType="SLGC";
		    	}
		    	else if (businessKey.substring(0, Math.min(businessKey.length(), 4)).equalsIgnoreCase("SLGT"))
		    	{
		    		requestType="SLGT";

		    	}
		    	else if(businessKey.substring(0, Math.min(businessKey.length(), 4)).equalsIgnoreCase("SNNC"))
		    	{
		    		requestType="SNNC";

		    	}
		    	else if(businessKey.substring(0, Math.min(businessKey.length(), 4)).equalsIgnoreCase("SNRC"))
		    	{
		    		requestType="SNRC";
		    	}
		    	else if(businessKey.substring(0, Math.min(businessKey.length(), 4)).equalsIgnoreCase("SLGF"))
		    	{
		    		requestType="SLGF";

		    	}
		    	else if(businessKey.substring(0, Math.min(businessKey.length(), 4)).equalsIgnoreCase("SLGB"))
		    	{
		    		requestType="SLGB";

		    	}
		    	
		    	else
		    	{
		    		requestType="SLGC";
		    		
		    	}
		    
		    
		    obj.put("requestType", requestType);
		    logger.info("JSON passed for deliverConfiguration method"+obj);
	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        JSONParser parser = new JSONParser();
	        JSONObject json = (JSONObject) parser.parse(result);
	        logger.info("Output of deliverConfiguration method is " + result);
	        outputVar = json.get("output").toString();
	        logger.info("Output of deliverConfiguration method is " + outputVar);
	
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in deliverConfiguration method is ", e);
        }
		
		return outputVar;
	}
	
	@SuppressWarnings("unchecked")
	public String validateNetworkTest(String businessKey, String version) throws ParseException, IOException{
		String endURL = dus.s+"/C3P/NetworkTestValidation/networkCommandTest";
		//String endURL = "http://localhost:8024"+"/NetworkTestValidation/networkCommandTest";

		String outputVar = null;
        try{
        	logger.info("Inside validateNetworkTest method");
		    
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
	        
	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        JSONParser parser = new JSONParser();
	        JSONObject json = (JSONObject) parser.parse(result);
	        outputVar = json.get("output").toString();
	        logger.info("Output of validateNetworkTest method is " + outputVar);
	
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in validateNetworkTest method is ", e);
        }
		return outputVar;
	}
	
	@SuppressWarnings("unchecked")
	public String validateHealthCheckTest(String businessKey, String version) throws ParseException, IOException{
		String endURL = dus.s+"/C3P/HealthCheckTestValidation/healthcheckCommandTest";
		//String endURL = "http://localhost:8024"+"/HealthCheckTestValidation/healthcheckCommandTest";
		String outputVar = null;
        try{
        	logger.info("Inside validateHealthCheckTest method");
		    
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
	        
	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        JSONParser parser = new JSONParser();
	        JSONObject json = (JSONObject) parser.parse(result);
	        outputVar = json.get("output").toString();
	        logger.info("Output of validateHealthCheckTest method is " + outputVar);
	
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in validateHealthCheckTest method is ", e);
        }
		return outputVar;
	}
	
	@SuppressWarnings("unchecked")
	public String validateOthersCheckTest(String businessKey, String version) throws ParseException, IOException{
		String endURL = dus.s+"/C3P/OthersCheckTestValidation/otherscheckCommandTest";
		//String endURL = "http://localhost:8024"+"/OthersCheckTestValidation/otherscheckCommandTest";
		String outputVar = null;
        try{
        	logger.info("Inside others milestone method");
		    
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
	        
	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        JSONParser parser = new JSONParser();
	        JSONObject json = (JSONObject) parser.parse(result);
	        outputVar = json.get("output").toString();
	        logger.info("Output of validateHealthCheckTest method is " + outputVar);
	
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in validateHealthCheckTest method is ", e);
        }
		return outputVar;
	}
	@SuppressWarnings("unchecked")
	public String validateNetworkAudit(String businessKey, String version) throws ParseException, IOException{
		String endURL = dus.s+"/C3P/NetworkAuditTest/networkAuditCommandTest";
		//String endURL = "http://localhost:8024"+"/NetworkAuditTest/networkAuditCommandTest";
		String outputVar = null;
        try{
        	logger.info("Inside validateNetworkAudit milestone method");
		    
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
	        
	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        JSONParser parser = new JSONParser();
	        JSONObject json = (JSONObject) parser.parse(result);
	        outputVar = json.get("output").toString();
	        logger.info("Output of validateNetworkAudit method is " + outputVar);
	        
	     System.out.println(outputVar);
	
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in validateNetworkAudit method is ", e);
        }
		return outputVar;
	}
	@SuppressWarnings("unchecked")
	public String certifyReportforTTUTest(String businessKey, String version) throws ParseException, IOException{
		String endURL = dus.s+"/C3P/FinalReportForTTUTest/finalReportCreation";
		//String endURL = "http://localhost:8024"+"/FinalReportForTTUTest/finalReportCreation";

		String outputVar = null;
        try{
        	logger.info("Inside certifyReportforTTUTest method");
		    
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
	        
		    logger.info("requestId "+businessKey);
		    logger.info("version "+version);

	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        JSONParser parser = new JSONParser();
	        JSONObject json = (JSONObject) parser.parse(result);
	        outputVar = json.get("output").toString();
	        logger.info("Output of certifyReportforTTUTest method is " + outputVar);
	
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in certifyReportforTTUTest method is ", e);
        }
		return outputVar;
	}
	@SuppressWarnings("unchecked")
	public String PostUpgradeHealthCheck(String businessKey, String version) throws ParseException, IOException{
		String endURL = dus.s+"/C3P/OsUpgrade/HealthCheck";
		//String endURL = "http://localhost:8024"+"/OsUpgrade/HealthCheck";

		String outputVar = null;
        try{
        	logger.info("Inside certifyReportforTTUTest method");
		    
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
	        
	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        JSONParser parser = new JSONParser();
	        JSONObject json = (JSONObject) parser.parse(result);
	        outputVar = json.get("output").toString();
	        logger.info("Output of certifyReportforTTUTest method is " + outputVar);
	
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in certifyReportforTTUTest method is ", e);
        }
		return outputVar;
	}
	@SuppressWarnings("unchecked")
	public String selectRequestInDB(String businessKey,String version) throws ParseException, IOException{
		String endURL = dus.s+"/C3P/CreateScheduleReqDBService/selectRequestInDB";
		String result = null;
        try{
        	logger.info("Inside selectRequestInDB method");
		    
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
	        
	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        logger.info("Output of selectRequestInDB method is " + result);
	
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in selectRequestInDB method is ", e);
        }
		return result;
	}		
	
	@SuppressWarnings("unchecked")
	public void insertRequestInDB(String businessKey,String version,String processId,String user) throws ParseException, IOException{
		String endURL = dus.s+"/C3P/CreateScheduleReqDBService/insertRequestInDB";
		 try{
	        logger.info("Inside insertRequestInDB method");
		    
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
		    obj.put("processId", processId);
		    obj.put("user", user);
	        
	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	
	        in.close();
	        conn.disconnect();
	        logger.info("insertRequestInDB method is completed successfully");
		 }
		 catch(Exception e){
			 logger.error("Error in insertRequestInDB method is ", e);
		 }
	}		
	
	@SuppressWarnings("unchecked")
	public void updateTaskIDInDB(String processId,String taskId) throws ParseException, IOException{
		String endURL = dus.s+"/C3P/CreateScheduleReqDBService/updateTaskIDInDB";
		try{
	        logger.info("Inside updateTaskIDInDB method");
	        
		    JSONObject obj = new JSONObject();
		    obj.put("processId",processId);
		    obj.put("taskId", taskId);
	        
	        URL url = new URL(endURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	
	        in.close();
	        conn.disconnect();
	        logger.info("updateTaskIDInDB method is completed successfully");
		}
		catch(Exception e){
			logger.error("Error in updateTaskIDInDB method is ", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String performPreValidationTest(String businessKey, String version) throws ParseException, IOException{		
		String endURL = dus.s+"/C3P/DeviceReachabilityAndPreValidationTest/performPrevalidateTest";
		//String endURL ="http://localhost:8024"+"/DeviceReachabilityAndPreValidationTest/performPrevalidateTest"; 
		//String endURLVNF="http://localhost:8024"+"/vnfservices/prevalidateODL";;
		String endURLVNF=dus.s+"/C3P/vnfservices/prevalidateODL";;

		String outputVar = null;
        try{
        	logger.info("Inside performPreValidationTest method");
		    JSONObject obj = new JSONObject();
		    obj.put("requestId",businessKey);
		    obj.put("version", version);
	        
	        
	        URL url=null;
		    String type = businessKey.substring(0,
					Math.min(businessKey.length(), 4));
		    if(type.equalsIgnoreCase("SNRC"))
		    {
		        url = new URL(endURLVNF);
	
		    }
		    else
		    {
		    	 url = new URL(endURL);
		    }
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	
	        OutputStream os = conn.getOutputStream();
	        os.write(obj.toString().getBytes("UTF-8"));
	        os.close();
	
	        // read the response
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	        JSONParser parser = new JSONParser();
	        logger.info("Output of performPreValidationTest method is " + result);

	        JSONObject json = (JSONObject) parser.parse(result);
	        outputVar = json.get("output").toString();
	        logger.info("Output of performPreValidationTest method is " + outputVar);
	        
	        in.close();
	        conn.disconnect();
        }
        catch(Exception e){
        	logger.error("Error in performPreValidationTest method is ", e);
        }
		return outputVar;
	}
		
}
