# Use a imagem base do Maven para construir a aplicação
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Use a imagem base do Java 17 para executar a aplicação
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/bancosangue-0.0.1-SNAPSHOT.jar myapp.jar
EXPOSE 8080
CMD ["java", "-jar", "myapp.jar"]
