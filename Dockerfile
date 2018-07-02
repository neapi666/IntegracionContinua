FROM tomcat
MAINTAINER carlosarias@icreativa.com.co

# Install mysql-server and tomcat 7
RUN apt-get update && apt-get install -y lsb-release && \
    wget https://dev.mysql.com/get/mysql-apt-config_0.8.4-1_all.deb && \
    dpkg -i mysql-apt-config_0.8.4-1_all.deb && rm -f mysql-apt-config_0.8.4-1_all.deb && \

# Install packages
RUN apt-get -y install mysql-server pwgen supervisor && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Add image configuration and scripts
ADD run/start-mysqld.sh /start-mysqld.sh
ADD run/run.sh /run.sh
RUN chmod 755 /*.sh
ADD run/my.cnf /etc/mysql/conf.d/my.cnf
ADD run/supervisord-mysqld.conf /etc/supervisor/conf.d/supervisord-mysqld.conf

# Remove pre-installed database
RUN rm -rf /var/lib/mysql/*

# Add MySQL utils
ADD create_mysql_admin_user.sh /create_mysql_admin_user.sh
ADD mysql-setup.sh /mysql-setup.sh
RUN chmod 755 /*.sh

# Add volumes for MySQL
VOLUME ["/etc/mysql", "/var/lib/mysql"]

ADD dist/vacantes.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
