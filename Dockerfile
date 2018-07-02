FROM tomcat
MAINTAINER carlosarias@icreativa.com.co

ADD dist/vacantes.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
