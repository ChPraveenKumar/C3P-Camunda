package com.techm.c3p.workflow.camunda.bpm;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techm.c3p.workflow.camunda.common.LoggerUtil;
import com.techm.c3p.workflow.camunda.service.CamundaInvokeC3PRestService;

public class ScheduleReqWFStart implements JavaDelegate {

	private static final Logger logger = LoggerUtil
			.getApplicationLogger(ScheduleReqWFStart.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String businessKey = execution.getProcessBusinessKey();
		String processId = execution.getProcessInstanceId();
		Object version = execution.getVariable("version");
		Object user = execution.getVariable("user");
		
		if(execution.getCurrentActivityId().equals("T_C3P_ScheduleRequest_StartEvent1")){
			logger.info("Started Schedule Request WF for RequestID '" + businessKey +"'");
			logger.info("Process instance id for Schedule request is : " + processId);
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_ScheduleRequest_FetchRequestDetailsfromDB")){
			CamundaInvokeC3PRestService cs = new CamundaInvokeC3PRestService();
			//To insert process details into DB
			cs.insertRequestInDB(businessKey, (String) version, processId, (String) user);
			//To fetch schedule date from DB
			String scheduleDate = cs.selectRequestInDB(businessKey,(String) version);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
			String date1 = null;
			LocalDateTime localDateTime = null;
			try {
				localDateTime = LocalDateTime.ofInstant(simpleDateFormat.parse(scheduleDate).toInstant(), ZoneId.systemDefault());
				date1 = localDateTime.format(dateTimeFormatter);
			} catch (Exception e) {
				logger.error("Exception in ScheduleReqWFStart's execute method ", e);
			}
			execution.setVariable("data", date1);		
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_ScheduleRequest_WaitDuration")){
			logger.info("Wait duration for Scheduler is " + execution.getVariable("data"));
		}
		else if(execution.getCurrentActivityId().equals("T_C3P_ScheduleRequest_EndEvent")){
			logger.info("WF ended of Schedule request for '" + businessKey +"'");
		}
		else{
			logger.info("Not required activity");
		}
		
	}

}
