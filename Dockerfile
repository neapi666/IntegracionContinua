FROM tomcat
MAINTAINER carlosarias@icreativa.com.co

RUN  sudo apt-get update
RUN sudo apt-get upgrade
RUN echo mysql-server mysql-server/root_password select admin | debconf-set-selections
RUN echo mysql-server mysql-server/root_password_again select admin | debconf-set-selections
RUN apt-get -y install mysql-server


ADD dist/vacantes.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
