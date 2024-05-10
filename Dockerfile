FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY /target/*.jar /app/bookstore.jar
ENTRYPOINT ["java", "-jar", "bookstore.jar"]
EXPOSE 8080
