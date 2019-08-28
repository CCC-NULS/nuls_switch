FROM openjdk:8u131-jdk-alpine

MAINTAINER chenxue

#将指定的jar文件复制到容器中


COPY target/switch.jar /usr/local/

#执行jar文件

ENTRYPOINT ["java" ,"-jar","/usr/local/switch.jar"]

EXPOSE 8761