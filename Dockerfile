# Build da aplicação
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .

# Imagem final apenas com o .jar
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/quoted-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]