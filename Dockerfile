FROM openjdk:11-jre-slim-buster

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} springboot-demo-app.jar

ENTRYPOINT ["java","-jar","/springboot-demo-app.jar"]