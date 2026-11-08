# syntax=docker/dockerfile:1

# With no user privileges 
FROM eclipse-temurin:25-jre

WORKDIR /app

COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

# Build and run the application

# java -jar target/demo-0.0.1-SNAPSHOT.jar

# ./gradlew build
# docker build --build-arg 'JAR_FILE=build/libs/*-SNAPSHOT.jar' -t springio/gs-spring-boot-docker .
# docker run -p 8080:8080 springio/gs-spring-boot-docker

# Log entries

#  :: Spring Boot ::                (v4.1.1)

# 2026-09-30T12:47:53.827Z  INFO 1 --- [           main] hello.Application                        : Starting Application v0.0.1-SNAPSHOT using Java 17.0.19 with PID 1 (/app.jar started by spring in /)
# ...
