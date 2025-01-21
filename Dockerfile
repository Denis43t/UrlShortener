# Use the official Spring Boot image as a base
FROM openjdk:21-jdk-slim
WORKDIR /app

COPY ./target/*.jar /app/app.jar

EXPOSE 9999

RUN apt-get update && apt-get install -y postgresql-client

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
CMD ["-Dspring.profiles.active=prod"]
