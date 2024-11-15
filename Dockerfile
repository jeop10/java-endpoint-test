# FROM openjdk:11
FROM amazoncorretto:17-alpine3.17
WORKDIR /app
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar app.jar

#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]