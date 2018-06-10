FROM tomcat:8.0-jre8-alpine

# set server defautlt setting 
RUN apk --update add tzdata perl curl openssh openrc supervisor && \
    rm -rf /var/cache/apk/*
ENV TZ Asia/Tokyo

# delete default directry & set tomcat config
# RUN rm -rf /usr/local/tomcat/webapps/ROOT \
#     /usr/local/tomcat/webapps/docs \
#     /usr/local/tomcat/webapps/examples
ADD ./conf/tomcat-users.xml ./conf/server.xml ./conf/web.xml /usr/local/tomcat/conf/

# access to velocity frmo jboss
ENV JAVA_OPTS="-Dspring.profiles.active=local"
ENV JPDA_ADDRESS=8000
ENV JPDA_TRANSPORT=dt_socket

# start tomcat & sshd
COPY ./conf/supervisord.conf /etc/supervisor/conf.d/supervisord.conf
CMD /usr/bin/supervisord -c /etc/supervisor/conf.d/supervisord.conf
