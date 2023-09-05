FROM openjdk:11-alpine-slim

COPY build/libs/shopping-mall-spring-0.0.1-SNAPSHOT.jar /app.jar

CMD ["java", "-jar", "/app.jar"]
