# Etapa 1: Build da aplicação
FROM gradle:9-jdk25 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test --no-daemon

# Etapa 2: Execução leve
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]