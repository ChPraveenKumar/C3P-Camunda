package com.techm.c3p.workflow.camunda.bpm;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techm.c3p.workflow.camunda.common.LoggerUtil;
import com.techm.c3p.workflow.camunda.service.CamundaInvokeC3PPythonRestService;
import com.techm.c3p.workflow.camunda.service.CamundaInvokeC3PRestService;

public class SONextRunWorkflowStart implements JavaDelegate{

	private static final Logger logger = LoggerUtil
			.getApplicationLogger(SONextRunWorkflowStart.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String businessKey = execution.getProcessBusinessKey();
		Object version = execution.getVariable("version");
		String processId = execution.getProcessInstanceId();
	
		if(execution.getCurrentActivityId().equals("T_C3P_SONextRun_StartEvent1")){
			logger.info("Started SO next run wf '" + businessKey +"'");
			logger.info("Process instance id for SO next run wf is : " + processId);
		
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_SONextRun_Nextrun")){
			logger.info("Inside next run '" + businessKey +"'");
			logger.info("Process instance id for SO next run wf is : " + processId);
			CamundaInvokeC3PPythonRestService pythonservice=new CamundaInvokeC3PPythonRestService();
			String result=pythonservice.nextRun(businessKey, (String) version);
			 execution.setVariable("data", result);
			// execution.setVariable("data", "true");
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_SONextRun_CallNotificationApi")){
			logger.info("Inside call notification api '" + businessKey +"'");
			logger.info("Process instance id for SO next run wf is : " + processId);
			CamundaInvokeC3PPythonRestService pythonservice=new CamundaInvokeC3PPythonRestService();
			String result=pythonservice.notificationAPI(businessKey, (String) version);
			 execution.setVariable("data", result);
			 //execution.setVariable("data", "true");

		}
		else if(execution.getCurrentActivityId().equals("T_C3P_SONextRun_EndEvent")){
			logger.info("Ended SO next run wf '" + businessKey +"'");

		}
		
	
	}

}
