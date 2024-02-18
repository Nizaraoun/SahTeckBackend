# Use an OpenJDK base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/my-spring-boot-app.jar /app/my-spring-boot-app.jar

# Specify the command to run the Spring Boot application
CMD ["java", "-jar", "/app/my-spring-boot-app.jar"]
