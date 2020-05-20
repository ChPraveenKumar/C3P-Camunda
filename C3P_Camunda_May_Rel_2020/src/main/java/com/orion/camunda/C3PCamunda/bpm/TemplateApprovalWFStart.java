package com.orion.camunda.C3PCamunda.bpm;


import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.orion.camunda.C3PCamunda.common.LoggerUtil;
import com.orion.camunda.C3PCamunda.service.CamundaInvokeC3PRestService;

public class TemplateApprovalWFStart implements JavaDelegate{
	
	private static final Logger logger = LoggerUtil
			.getApplicationLogger(TemplateApprovalWFStart.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String businessKey = execution.getProcessBusinessKey();
		String version = (String) execution.getVariable("version");
		String processId = execution.getProcessInstanceId();
		String approver = (String) execution.getVariable("approver");
		
		if(execution.getCurrentActivityId().equals("T_C3P_WFStartEvent")){
			logger.info("Started Template Approval WF for Template ID " + businessKey);
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_AllocateTemplateApprovalReqtoPredefinedApprover")){
			execution.setVariable("data", approver);
			//To insert process details into DB
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			cs.insertRequestInDB(businessKey,version, processId, approver);
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_SetTemplateStatusApproved") || execution.getCurrentActivityId().equals("T_C3P_ChangeTemplateStatusRejected")){
			
		}
		else if((execution.getCurrentActivityId().equals("T_C3P_EndEvent_1"))){
			String status = (String) execution.getVariable("status");
			logger.info("Template Approval WF Ended with status " + status);
		}
		else if((execution.getCurrentActivityId().equals("T_C3P_EndEvent_2"))){
			String status = (String) execution.getVariable("status");
			logger.info("Template Approval WF Ended with status " + status);
		}
		else{
			logger.info("Not required Activity");
		}
		
		
	}

}
