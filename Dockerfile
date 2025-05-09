FROM openjdk:21-slim-bullseye
WORKDIR /app
COPY /target/user_subscriptions-0.0.1.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]