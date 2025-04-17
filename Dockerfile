FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/*hotel-review-app*.jar hotel-review-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hotel-review-app.jar"]
