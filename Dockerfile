FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /app

COPY pom.xml .
COPY common common/
COPY sender sender/

RUN mvn clean package -pl :common,:sender -am -DskipTests --no-transfer-progress

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /app/sender/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]