FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -Dmaven.main.skip=true -Dmaven.test.skip=true

COPY src ./src
RUN mvn clean package -DskipTests -Dmaven.test.skip=true -T 1C

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN apk update && apk add --no-cache curl && rm -rf /var/cache/apk/*

ARG JAR_FILE=target/saraEmprega-0.0.1-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} app.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
