FROM tomcat
MAINTAINER XZIGMA

RUN echo mysql-server mysql-server/root_password select admin | debconf-set-selections
RUN echo mysql-server mysql-server/root_password_again select admin | debconf-set-selections
RUN docker exec -ti -u root xzigma/integracioncontinua bash
RUN sudo apt-get update -y
RUN sudo apt-get upgrade -y
RUN apt-get -y install mysql-server


ADD dist/vacantes.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
