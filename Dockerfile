FROM openjdk:14-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline


COPY src src
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]