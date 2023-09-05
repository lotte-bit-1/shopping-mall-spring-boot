FROM adoptopenjdk/openjdk11 AS builder

ENV DATABASE_URL e
ENV DATABASE_USERNAME e
ENV DATABASE_PASSWORD e


COPY shopping-mall-spring-boot/gradlew .
COPY shopping-mall-spring-boot/gradle gradle
COPY shopping-mall-spring-boot/build.gradle .
COPY shopping-mall-spring-boot/settings.gradle .
COPY shopping-mall-spring-boot/src src
RUN chmod +x ./gradlew
RUN ./gradlew clean bootWar

FROM adoptopenjdk/openjdk11
COPY --from=builder build/libs/shopping-mall-spring-0.0.1-SNAPSHOT-plain.war app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", \
    "-Dspring.datasource.url=${DATABASE_URL}", \
    "-Dspring.datasource.username=${DATABASE_USERNAME}", \
    "-Dspring.datasource.password=${DATABASE_PASSWORD}", \
    "/app.war"]
