FROM tomcat:9.0.20-jre8
#RUN echo "export JAVA_OPTS=\"-Dapp.env=xtest\"" > /usr/local/tomcat/bin/setenv.sh
#https://geekflare.com/tomcat-login-problem/
ADD docker/context.xml $CATALINA_HOME/webapps/manager/META-INF
ADD docker/tomcat-users.xml $CATALINA_HOME/conf/
RUN rm -rf $CATALINA_HOME/webapps/ROOT
COPY target/note-list-0.0.1-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war
CMD ["catalina.sh", "run"]
