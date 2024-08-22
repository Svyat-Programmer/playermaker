FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/playermaker.jar /app/playermaker.jar

ENTRYPOINT ["java", "-jar", "/app/playermaker.jar"]

EXPOSE 8080
