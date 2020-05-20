FROM woahbase/alpine-wildfly
EXPOSE 8080
ADD target/C3PCamunda.war /opt/jboss/wildfly/standalone/deployments/

