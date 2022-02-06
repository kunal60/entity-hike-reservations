FROM openjdk:11-jdk-slim as builder
MAINTAINER "Kunal Malhotra"
WORKDIR /usr/app

ARG JAR_FILE=./target/entity-trail-reservations.jar
COPY ${JAR_FILE} entity-trail-reservations.jar
ENTRYPOINT ["java", "-jar", "/usr/app/entity-trail-reservations.jar"]