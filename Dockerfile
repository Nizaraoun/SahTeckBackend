FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/*.jar SAHTECH.jar
Expose 8080
ENTRYPOINT ["java","-jar","/SAHTECH.jar"]
