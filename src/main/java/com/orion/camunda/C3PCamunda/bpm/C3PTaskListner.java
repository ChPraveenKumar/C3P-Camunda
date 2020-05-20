package com.orion.camunda.C3PCamunda.bpm;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.json.simple.parser.ParseException;

import com.orion.camunda.C3PCamunda.common.LoggerUtil;
import com.orion.camunda.C3PCamunda.service.CamundaInvokeC3PRestService;

public class C3PTaskListner implements TaskListener{
	
	private static final Logger logger = LoggerUtil
			.getApplicationLogger(C3PTaskListner.class);

	@Override
	public void notify(DelegateTask task) {
		// TODO Auto-generated method stub
		if(TaskListener.EVENTNAME_CREATE.equals(task.getEventName())){
			if((task.getTaskDefinitionKey().equals("UT_C3P_ApproverToAccessTemplateAvailableforApproval")) || (task.getTaskDefinitionKey().equals("UT_C3P_NewRequest_AssignNotifyFEtoEstablishConnection"))){
				String processId = task.getProcessInstanceId();
				String taskId = task.getId();
				logger.info("Waiting for user task to get completed and task id is " + taskId);
				CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
				try {
					cs.updateTaskIDInDB(processId, taskId);
				} catch (ParseException | IOException e) {
					// TODO Auto-generated catch block
					logger.error("Exception in C3PTaskListner's notify method is ", e);
				}
			}
		}
		
		if(TaskListener.EVENTNAME_COMPLETE.equals(task.getEventName())){
			if((task.getTaskDefinitionKey().equals("UT_C3P_ApproverToAccessTemplateAvailableforApproval")) || (task.getTaskDefinitionKey().equals("UT_C3P_NewRequest_AssignNotifyFEtoEstablishConnection"))){
				String status = (String) task.getVariable("status");
				task.setVariable("data", status);
			}
		}
	}

}
