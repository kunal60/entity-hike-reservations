FROM openjdk:11-jdk-slim as builder
MAINTAINER "Kunal Malhotra"
WORKDIR /usr/app

ARG JAR_FILE=./target/entity-hike-reservations.jar
COPY ${JAR_FILE} entity-hike-reservations.jar
ENTRYPOINT ["java", "-jar", "/usr/app/entity-hike-reservations.jar"]