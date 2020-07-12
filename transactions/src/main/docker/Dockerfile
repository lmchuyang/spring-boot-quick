FROM java:8
VOLUME /tmp
RUN mkdir /app
ADD microservice-service-1.3.1.jar /app/app.jar
ADD runboot.sh /app/
RUN bash -c 'touch /app/app.jar'
WORKDIR /app
RUN chmod a+x runboot.sh
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \ 
    echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 3020
ENV LANG C.UTF-8
CMD /app/runboot.sh