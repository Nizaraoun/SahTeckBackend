# Use Maven to build the application
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Use OpenJDK for running the application
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar SAHTECH.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SAHTECH.jar"]
