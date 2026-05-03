# Étape 1 : Construction (Build) avec Maven
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
# On copie d'abord le pom.xml pour télécharger les dépendances (mise en cache Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# On copie ensuite le code source et on compile
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécution (Run) avec une image légère
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# On ne récupère que le fichier .jar généré à l'étape précédente
COPY --from=builder /app/target/*.jar app.jar

# On expose le port sur lequel tourne Spring Boot (je suppose 8090 vu vos requêtes)
EXPOSE 8090
