# Use a base image with Java and Maven installed
FROM maven:3.8.4-openjdk-17 AS build  # Update the Maven version and Java version accordingly

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Download dependencies and cache them in Docker layer
RUN mvn dependency:go-offline

# Copy the application source code to the container
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image with Java installed
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the container
COPY --from=build /app/target/my-spring-boot-app.jar ./my-spring-boot-app.jar

# Expose the port the application runs on
EXPOSE 8080

# Specify the command to run the Spring Boot application
CMD ["java", "-jar", "my-spring-boot-app.jar"]

