FROM mysql/mysql-server:latest

ENV JAVA_VERSION 8u92
ENV BUILD_VERSION b14
ENV DNS_HOSTS "nameserver 8.8.8.8 \nnameserver 8.8.4.4"

RUN rm -rf /var/cache/yum/*

RUN echo "$DNS_HOSTS" >> /etc/resolv.conf && yum -y upgrade && yum -y install wget
RUN echo "$DNS_HOSTS" >> /etc/resolv.conf && wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/$JAVA_VERSION-$BUILD_VERSION/jdk-$JAVA_VERSION-linux-x64.rpm" -O /tmp/jdk-8-linux-x64.rpm
RUN echo "$DNS_HOSTS" >> /etc/resolv.conf && yum -y install /tmp/jdk-8-linux-x64.rpm

RUN mkdir -p /tmp/FullTestIndex/indexes/
RUN mkdir -p /tmp/FullTestIndex/files/

VOLUME /tmp/FullTestIndex/files/

RUN [[ -d /docker-entrypoint-initdb.d ]] || mkdir /docker-entrypoint-initdb.d
RUN echo "CREATE DATABASE IF NOT EXISTS fullTestSearchDB" > /docker-entrypoint-initdb.d/init_default_db.sql


ADD springsearch-0.0.1-SNAPSHOT.jar /app.jar
RUN sh -c 'touch /app.jar'
#
#ENV SERVER_PORT \
#    SPRING_DATASOURCE_URL \
#    SPRING_DATASOURCE_USERNAME \
#    SPRING_DATASOURCE_PASSWORD \
#    SPRING_DATASOURCE_DRIVER-CLASS-NAME \
#    LOGGING_LEVEL_ROOT

#ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

COPY docker-entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]

EXPOSE 3306 33060 8080 80
CMD ["mysqld"]
