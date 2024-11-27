FROM tomcat:10.1-jdk17

WORKDIR /usr/local/tomcat

COPY target/pbz-0.0.1.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]