# Docker multi-stage build

# 1. Building the App with Maven
FROM maven:3-jdk-11

WORKDIR /app
COPY pom.xml ./

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn install


# Just using the build artifact and then removing the build-container
FROM openjdk:17-jdk as finalApp

MAINTAINER XUEyuxin

VOLUME /tmp

# Add Spring Boot app.jar to Container
COPY --from=0 "/app/target/spring-1.0.0.jar" app.jar

ENV JAVA_OPTS=""

EXPOSE 8080

# Fire up our Spring Boot app by default
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]