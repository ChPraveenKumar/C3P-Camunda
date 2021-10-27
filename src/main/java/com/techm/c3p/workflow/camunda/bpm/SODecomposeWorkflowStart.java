package com.techm.c3p.workflow.camunda.bpm;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techm.c3p.workflow.camunda.common.LoggerUtil;
import com.techm.c3p.workflow.camunda.service.CamundaInvokeC3PPythonRestService;

public class SODecomposeWorkflowStart implements JavaDelegate {

	private static final Logger logger = LoggerUtil
			.getApplicationLogger(SODecomposeWorkflowStart.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String businessKey = execution.getProcessBusinessKey();
		Object version = execution.getVariable("version");
		String processId = execution.getProcessInstanceId();

		if (execution.getCurrentActivityId().equals(
				"T_C3P_ScheduleRequest_StartEvent1")) {
			logger.info("Started Decompose WF for RequestID '" + businessKey
					+ "'");
			logger.info("Process instance id for New request is : " + processId);
		} else if (execution.getCurrentActivityId().equals(
				"T_C3P_SODecompose_Decompose")) {
			logger.info("Inside So decompose step"+ businessKey
					+ "'");
			logger.info("Process instance id for So decompose is : " + processId);
			CamundaInvokeC3PPythonRestService pythonservice=new CamundaInvokeC3PPythonRestService();
			String result=pythonservice.decomposeSo(businessKey, (String) version);
			execution.setVariable("data", result);
			//execution.setVariable("data", "true");
		}

		else if (execution.getCurrentActivityId().equals(
				"T_C3P_SODecompose_EndEvent")) {
			logger.info("WF ended of So decompose for '" + businessKey + "'");
		} 

	}

}
