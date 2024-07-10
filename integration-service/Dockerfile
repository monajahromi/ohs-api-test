FROM maven:3.8.4-openjdk-17 AS build
# Set the working directory inside the container
WORKDIR /app
# Copy the project's pom.xml file into the container
COPY pom.xml .
# Download the project dependencies
RUN mvn dependency:go-offline
COPY src/ /app/src/

RUN mvn clean -f pom.xml
RUN mvn compile -f pom.xml
RUN mvn package -f pom.xml
# Use the OpenJDK runtime as the final image
FROM maven:3.8.4-openjdk-17
# Set the working directory inside the container
WORKDIR /app

ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
## Copy the compiled JAR file from the build stage
COPY --from=build /app/${JAR_FILE} /app/integration-service.jar

# Specify the command to run your application
CMD ["java", "-jar", "integration-service.jar"]