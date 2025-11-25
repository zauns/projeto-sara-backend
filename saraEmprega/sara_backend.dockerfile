FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN apk update && \
    apk add --no-cache curl

ARG JAR_FILE=target/saraEmprega-0.0.1-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} app.jar

#COPY jwtkeys ./jwtkeys

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
