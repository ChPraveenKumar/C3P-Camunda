package com.orion.camunda.C3PCamunda.bpm;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.orion.camunda.C3PCamunda.common.LoggerUtil;
import com.orion.camunda.C3PCamunda.service.CamundaInvokeC3PRestService;

public class NewRequestWFStart implements JavaDelegate{

	private static final Logger logger = LoggerUtil
			.getApplicationLogger(NewRequestWFStart.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String businessKey = execution.getProcessBusinessKey();
		Object version = execution.getVariable("version");
		String processId = execution.getProcessInstanceId();
		Object user = execution.getVariable("user");
		Object request=execution.getVariable("requestType");
		
	
		if(execution.getCurrentActivityId().equals("StartEvent_1")){
			logger.info("Started New Request WF for RequestID '" + businessKey +"'");
			logger.info("Process instance id for New request is : " + processId);
			//To insert process details into DB			
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			cs.insertRequestInDB(businessKey, (String) version, processId, (String) user);
			
			
		}
		/*else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_TriggertoCheckRequestType"))
		{
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.checkRequestType(businessKey, (String)version, (String)request);
		    execution.setVariable("data", result);
		}*/

		else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_TriggertoCheckDeviceReachability")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.checkDeviceReachability(businessKey,(String) version);
		    execution.setVariable("data", result);
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_PerformDevicePreTest")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.performPreValidationTest(businessKey,(String) version);
		    execution.setVariable("data", result);
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_DeliverConfiguartion1")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.deliverConfiguration(businessKey,(String) version,request);
		    execution.setVariable("data", result);
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_PerformNetworkTest")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.validateNetworkTest(businessKey,(String) version);
		    execution.setVariable("data",result);
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_PerformHealthTest")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.validateHealthCheckTest(businessKey,(String) version);
		    execution.setVariable("data",result);
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_PerformNetworkAudit")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.validateNetworkAudit(businessKey,(String) version);
		    execution.setVariable("data",result);
		}
		
		else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_PerformOthersTest")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.validateOthersCheckTest(businessKey,(String) version);
		    execution.setVariable("data",result);
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_CertifyTheDevicetobeUsedonNetwork")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.certifyReportforTTUTest(businessKey,(String) version);
		    execution.setVariable("data",result);
		}
		
		else if(execution.getCurrentActivityId().equals("T_C3P_NewRequest_DeliverBasicConfSuccessfully")){
			String status = (String) execution.getVariable("status");
		    execution.setVariable("data",status);
		}
		else if(execution.getCurrentActivityId().equals("EndEvent_15zjpyh")){
			logger.info("WF ended of New request for '" + businessKey +"'");
		}
		else if(execution.getCurrentActivityId().equals("EndEvent_1x8soua")){
			logger.info("WF on Hold for New request for '" + businessKey +"'");
		}
		else{
			logger.info("Not required activity");
		}
	
	}

}
