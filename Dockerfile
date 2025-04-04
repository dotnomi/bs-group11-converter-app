# Final Stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Install curl
RUN apk --no-cache add curl=8.12.1-r1

# Copy the backend JAR file
COPY target/*.jar /app/midas.jar

# Expose ports
EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=5m --timeout=3s \
    CMD ["curl", "-f", "http://localhost:8080/q/health/live"]

# Add a user and switch to it
RUN adduser -D srv_user
USER srv_user

# Start application
CMD ["java", "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", "-jar", "/app/midas.jar"]
