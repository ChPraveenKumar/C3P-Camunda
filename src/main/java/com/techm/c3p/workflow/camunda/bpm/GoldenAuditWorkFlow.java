package com.techm.c3p.workflow.camunda.bpm;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techm.c3p.workflow.camunda.common.LoggerUtil;
import com.techm.c3p.workflow.camunda.service.CamundaInvokeC3PRestService;

public class GoldenAuditWorkFlow implements JavaDelegate{

	private static final Logger logger = LoggerUtil
			.getApplicationLogger(GoldenAuditWorkFlow.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String businessKey = execution.getProcessBusinessKey();
		Object version = execution.getVariable("version");
		String processId = execution.getProcessInstanceId();
		if(execution.getCurrentActivityId().equals("T_C3P_GoldenAudit_StartEvent1")){
			logger.info("Started Golden Audit Configuration FLow '" + businessKey +"'");
			logger.info("Process instance id for SO next run wf is : " + processId);
		
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_Golde_Audit_PreProcess")){
			logger.info("Inside Pre Process '" + businessKey +"'");			
			CamundaInvokeC3PRestService service=new CamundaInvokeC3PRestService();
			String result=service.preProcess(businessKey, (String) version);
			logger.info(result);
			execution.setVariable("data", result);			 
		}	
		else if(execution.getCurrentActivityId().equals("T_C3P_Golden_Audit")){
			logger.info("Inside Golden Audit Configuration " + businessKey +"'");			
			CamundaInvokeC3PRestService service=new CamundaInvokeC3PRestService();
			String result=service.validateNetworkAudit(businessKey, (String) version);
			 execution.setVariable("data", result);			 
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_Golden_Audit_Report")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.certifyReportforTTUTest(businessKey,(String) version);
		    execution.setVariable("data",result);
		}else if(execution.getCurrentActivityId().equals("EndEvent_05ly3qs")){
			logger.info("Ended SO next run " + businessKey +"'");

		}
		
	}

}
