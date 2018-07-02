FROM tomcat
MAINTAINER carlosarias@icreativa.com.co

RUN echo mysql-server mysql-server/root_password select admin | debconf-set-selections
RUN echo mysql-server mysql-server/root_password_again select admin | debconf-set-selections
RUN sudo apt-get update -y
RUN sudo apt-get upgrade -y
RUN apt-get -y install mysql-server

RUN useradd -m docker && echo "docker:docker" | chpasswd && adduser docker sudo

USER docker
CMD /bin/bash


ADD dist/vacantes.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
