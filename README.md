# Spring.io users api starting point

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.6/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.0.6/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.0.6/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Start
```bash

# add in gradle.build dependencies
developmentOnly 'org.springframework.boot:spring-boot-devtools'

# terminal 1
.\gradlew.bat bootRun

# create migration


# watch mode development tools terminal 2
.\gradlew.bat classes --continuous

# for tests
.\gradlew.bat test --continuous

```

## Run with docker and Spring Boot
### Build a Docker Image with the Spring Boot Build Plugin
You can build a tagged docker image  (Dockerfile is ignored):

```bash
./gradlew bootBuildImage --imageName=springio/gs-spring-boot-docker
```

You have a locally tagged image, and you can run it like this:
```bash
docker run -p 8080:8080 -t springio/gs-spring-boot-docker
```

Gradle add dependencies
```bash
# install new implementation 
.\gradlew clean build --refresh-dependencies

# compile class paths
.\gradlew dependencies --configuration compileClasspath

# clean java compile for deps
.\gradlew clean compileJava --refresh-dependencies

# verif for exemple : spring-session-jdbc
.\gradlew dependencyInsight --dependency jackson-databind --configuration compileClasspath
```

