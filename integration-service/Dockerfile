# Stage 1: Build stage
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copy the project's pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src/ /app/src/
RUN mvn clean compile package -DskipTests

# Stage 2: Runtime stage
FROM maven:3.8.4-openjdk-17

WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/*.jar integration-service.jar

# Specify the command to run your application
CMD ["java", "-jar", "integration-service.jar"]
