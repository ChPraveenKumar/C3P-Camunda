package com.techm.orion.camunda.bpm;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techm.orion.camunda.common.LoggerUtil;
import com.techm.orion.camunda.service.CamundaInvokeC3PPythonRestService;
import com.techm.orion.camunda.service.CamundaInvokeC3PRestService;

public class FWUpgradeWorkflowStart implements JavaDelegate{

	private static final Logger logger = LoggerUtil
			.getApplicationLogger(FWUpgradeWorkflowStart.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String businessKey = execution.getProcessBusinessKey();
		Object version = execution.getVariable("version");
		String processId = execution.getProcessInstanceId();
	
		if(execution.getCurrentActivityId().equals("T_C3P_FWUpgrade_StartEvent1")){
			logger.info("Started SO next run wf '" + businessKey +"'");
			logger.info("Process instance id for SO next run wf is : " + processId);
		
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_FWUpgrade_Login")){
			logger.info("Inside Login '" + businessKey +"'");
			logger.info("Process instance id wf is : " + processId);
			CamundaInvokeC3PRestService service=new CamundaInvokeC3PRestService();
			String result=service.fwuLogin(businessKey, (String) version);
			 execution.setVariable("data", result);
			 //execution.setVariable("data", "true");
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_FWUpgrade_CheckFlash")){
			logger.info("Inside Check flash '" + businessKey +"'");
			logger.info("Process instance id wf is : " + processId);
			CamundaInvokeC3PRestService service=new CamundaInvokeC3PRestService();
			String result=service.fwuCheckFlashSize(businessKey, (String) version);
			 execution.setVariable("data", result);
			 //execution.setVariable("data", "true");

		}
		else if(execution.getCurrentActivityId().equals("T_C3P_FWUpgrade_Backup")){
			logger.info("Inside Backup '" + businessKey +"'");
			logger.info("Process instance id wf is : " + processId);
			CamundaInvokeC3PRestService service=new CamundaInvokeC3PRestService();
			String result=service.fwuBackup(businessKey, (String) version);
			 execution.setVariable("data", result);
			 //execution.setVariable("data", "true");

		}
		else if(execution.getCurrentActivityId().equals("T_C3P_FWUpgrade_OSDownload")){
			logger.info("Inside OS Download '" + businessKey +"'");
			logger.info("Process instance id wf is : " + processId);
			CamundaInvokeC3PRestService service=new CamundaInvokeC3PRestService();
			String result=service.fwuOsDownload(businessKey, (String) version);
			 execution.setVariable("data", result);
			 //execution.setVariable("data", "true");

		}
		else if(execution.getCurrentActivityId().equals("T_C3P_FWUpgrade_BootSystemFlash")){
			logger.info("Inside boot system flash '" + businessKey +"'");
			logger.info("Process instance id wf is : " + processId);
			CamundaInvokeC3PRestService service=new CamundaInvokeC3PRestService();
			String result=service.fwuBootSystemFlash(businessKey, (String) version);
			 execution.setVariable("data", result);
			 //execution.setVariable("data", "true");

		}
		else if(execution.getCurrentActivityId().equals("T_C3P_FWUpgrade_Reload")){
			logger.info("Inside reload '" + businessKey +"'");
			logger.info("Process instance id wf is : " + processId);
			CamundaInvokeC3PRestService service=new CamundaInvokeC3PRestService();
			String result=service.fwuReload(businessKey, (String) version);
			 execution.setVariable("data", result);
			 //execution.setVariable("data", "true");

		}
		else if(execution.getCurrentActivityId().equals("T_C3P_FWUpgrade_Postlogin")){
			logger.info("Inside postlogin '" + businessKey +"'");
			logger.info("Process instance id wf is : " + processId);
			CamundaInvokeC3PRestService service=new CamundaInvokeC3PRestService();
			String result=service.fwuPostLogin(businessKey, (String) version);
			 execution.setVariable("data", result);
			 //execution.setVariable("data", "true");

		}
		else if(execution.getCurrentActivityId().equals("T_C3P_SONextRun_EndEvent")){
			logger.info("Ended SO next run wf '" + businessKey +"'");

		}
		
	
	}

}
