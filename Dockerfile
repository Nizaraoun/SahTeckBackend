<<<<<<< HEAD
FROM maven:3.8.5-jdk-17 AS build
=======
FROM maven:3.8.4-openjdk-17 AS build
>>>>>>> 6d6f1dc4a81ed32dd368f140021e165d9f4865b9
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/*.jar SAHTECH.jar
Expose 8080
ENTRYPOINT ["java","-jar","/SAHTECH.jar"]
