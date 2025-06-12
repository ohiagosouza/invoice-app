# Build da aplicação
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .

# Imagem final apenas com o .jar
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/quoted-0.0.1-SNAPSHOT.jar quoted-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "quoted-0.0.1-SNAPSHOT.jar"]