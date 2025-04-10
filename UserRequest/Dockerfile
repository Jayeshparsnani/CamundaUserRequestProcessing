# Use a lightweight Java 21 runtime base image
FROM eclipse-temurin:21-jdk

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file from target directory into the container
COPY target/*.jar app.jar

# Expose the port your Spring Boot app runs on (default is 8080)
EXPOSE 8088

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
