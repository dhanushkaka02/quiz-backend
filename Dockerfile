# Build stage: compile and package the Spring Boot JAR
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q dependency:go-offline

COPY src ./src
RUN mvn clean package

# Runtime stage: run packaged JAR
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/quiz-system-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
# -Content -Path "C:\Users\2494589\Downloads\quiz-system\quiz-system\Dockerfile" -Encoding ASCII