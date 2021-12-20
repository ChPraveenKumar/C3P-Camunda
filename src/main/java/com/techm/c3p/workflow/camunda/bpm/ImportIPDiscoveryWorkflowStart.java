package com.techm.c3p.workflow.camunda.bpm;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techm.c3p.workflow.camunda.common.LoggerUtil;
import com.techm.c3p.workflow.camunda.service.CamundaInvokeC3PRestService;

public class ImportIPDiscoveryWorkflowStart implements JavaDelegate{

	private static final Logger logger = LoggerUtil
			.getApplicationLogger(ImportIPDiscoveryWorkflowStart.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String businessKey = execution.getProcessBusinessKey();
		Object status = execution.getVariable("version");
		String processId = execution.getProcessInstanceId();
		Object user = execution.getVariable("user");
		Object sourceSystem = execution.getVariable("requestType");
				
	
		if(execution.getCurrentActivityId().equals("StartEvent_1")){
			//initiate workflow
			logger.info("Started the workflow for Import IPs '" + businessKey +"'");
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			cs.insertRequestInDB(businessKey, (String) status, processId, (String) user);
		}
		else if(execution.getCurrentActivityId().equals("Activity_1_StageToDashboard")){
			logger.info("Staging table data to respective dashboard method");
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.stagingDataToRespectiveDashboard(businessKey, (String) status, (String) user);
			logger.info("Staging result "+result);
			execution.setVariable("data", result);
		}
		else if(execution.getCurrentActivityId().equals("Activity_2_PerformDiscovery")){
			logger.info("Perform module for every row");
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.performModuleForEveryRow(businessKey, (String) status, (String) user, (String) sourceSystem);
			logger.info("perform module for every row result "+result);
			execution.setVariable("data", result);
		}
		else if(execution.getCurrentActivityId().equals("Activity_3_CopyIntoHistoryTable")){
			logger.info("copy staging data into history table method");
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			String result = cs.copyIntoHistoryTable(businessKey, (String) status, (String) user);
			logger.info("copy staging data into history table result "+result);
			execution.setVariable("data", result);
		}
		else if(execution.getCurrentActivityId().equals("End_Event_1")){
			logger.info("WF Ended for Import IPs Discovery '" + businessKey +"'");
		}
		else{
			logger.info("Not required activity");
		}
	
	}

}
