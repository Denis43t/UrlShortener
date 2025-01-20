# Use the official Spring Boot image as a base
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the entire application JAR to the container
COPY ./target/*.jar /app/app.jar

# Expose the port your application runs on
EXPOSE 9999

# Set environment variables
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/shortener_db
ENV SPRING_DATASOURCE_USERNAME=user
ENV SPRING_DATASOURCE_PASSWORD=user
ENV JWT_SECRET=token

# Install necessary dependencies
RUN apt-get update && apt-get install -y postgresql-client

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
CMD ["-Dspring.profiles.active=dev"]
