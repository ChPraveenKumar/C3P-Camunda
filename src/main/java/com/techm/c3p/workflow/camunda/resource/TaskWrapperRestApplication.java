package com.techm.c3p.workflow.camunda.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.camunda.bpm.engine.rest.impl.CamundaRestResources;

@ApplicationPath("/")
public class TaskWrapperRestApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();

		// add all camunda engine rest resources (or just add those that you actually
		// need).
		classes.addAll(CamundaRestResources.getResourceClasses());

		// mandatory
		classes.addAll(CamundaRestResources.getConfigurationClasses());
		return classes;
	}
}
