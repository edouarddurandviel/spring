## Spring.io users api

### Run with docker and Spring Boot. 
Build a Docker Image with the Spring Boot Build Plugin
You can build a tagged docker image  (Dockerfile is ignored):

```bash
./gradlew bootBuildImage --imageName=springio/gs-spring-boot-docker
```

You have a locally tagged image, and you can run it like this:
```bash
docker run -p 8080:8080 -t springio/gs-spring-boot-docker
```

---------------------------------------------

### Run with docker defining JAR_FILE.
```bash
./gradlew build
docker build --build-arg 'JAR_FILE=build/libs/*-SNAPSHOT.jar' -t springio/gs-spring-boot-docker .
docker run -p 8080:8080 springio/gs-spring-boot-docker
```


