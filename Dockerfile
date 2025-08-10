# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY Java\ Maps/mvnw ./
COPY Java\ Maps/.mvn/ .mvn/
COPY Java\ Maps/pom.xml ./

# Copy source code
COPY Java\ Maps/src/ src/

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the application with memory optimization
RUN ./mvnw clean package -DskipTests -Xmx256m

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/ || exit 1

# Run the application with memory limits
CMD ["java", "-Xmx256m", "-Xms128m", "-jar", "target/ug-campus-navigator-1.0.0.jar"]
# Updated for Docker build fix
