# Étape 1 : Construction (Build) avec Maven
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

# On télécharge les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# On compile le projet
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécution (Run)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# On récupère le .jar
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8090

# C'EST LA LIGNE CRUCIALE QUI MANQUAIT OU ÉTAIT MAL ÉCRITE
ENTRYPOINT ["java", "-jar", "app.jar"]