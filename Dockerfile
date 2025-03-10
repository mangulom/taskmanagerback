
FROM tomcat:9.0-jdk17

ENV CATALINA_OPTS="-Dserver.port=9090"

COPY target/taskmanager-1.0.war /usr/local/tomcat/webapps/taskmanager.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
