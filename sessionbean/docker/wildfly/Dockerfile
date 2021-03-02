FROM bitnami/wildfly:20.0.1
ADD sessionbean.war /bitnami/wildfly/data
FROM jboss/wildfly:20.0.1.Final
ADD sessionbean.war /opt/jboss/wildfly/standalone/deployments/