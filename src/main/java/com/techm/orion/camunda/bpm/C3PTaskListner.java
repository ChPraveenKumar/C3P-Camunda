package com.techm.orion.camunda.bpm;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.techm.orion.camunda.common.LoggerUtil;
import com.techm.orion.camunda.service.CamundaInvokeC3PRestService;

public class C3PTaskListner implements TaskListener {

	private static final Logger logger = LoggerUtil.getApplicationLogger(C3PTaskListner.class);

	@Override
	public void notify(DelegateTask task) {
		if (TaskListener.EVENTNAME_CREATE.equals(task.getEventName())
				&& ("UT_C3P_ApproverToAccessTemplateAvailableforApproval".equals(task.getTaskDefinitionKey())
						|| "UT_C3P_NewRequest_AssignNotifyFEtoEstablishConnection"
								.equals(task.getTaskDefinitionKey()))) {
			String processId = task.getProcessInstanceId();
			String taskId = task.getId();
			logger.info("Waiting for user task to get completed and task id is " + taskId);
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			cs.updateTaskIDInDB(processId, taskId);
			task.setVariable("data", task.getVariable("status"));
		}
		if (TaskListener.EVENTNAME_COMPLETE.equals(task.getEventName())
				&& ("UT_C3P_ApproverToAccessTemplateAvailableforApproval".equals(task.getTaskDefinitionKey())
						|| "UT_C3P_NewRequest_AssignNotifyFEtoEstablishConnection"
								.equals(task.getTaskDefinitionKey()))) {			
			String status = (String) task.getVariable("status");
			task.setVariable("data", status);
		}
		
		//Demo purpose adding this.		
		if(TaskListener.EVENTNAME_COMPLETE.equals(task.getEventName())) {
			//Demo
			logger.info("Inside EventComplete method : EventName"+task.getEventName());
		}
	}

}
