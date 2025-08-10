# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY "Java Maps/mvnw" ./
COPY "Java Maps/.mvn/" .mvn/
COPY "Java Maps/pom.xml" ./

# Copy source code
COPY "Java Maps/src/" src/

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/ug-campus-navigator-1.0.0.jar"]
