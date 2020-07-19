FROM openjdk:8-jdk-alpine

MAINTAINER Emanuel Almirante, emanuelalmirante@gmail.com

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080:8080
