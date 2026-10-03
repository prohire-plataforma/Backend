# Etapa 1: Compilar el proyecto con Maven y Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar en GlassFish 7 oficial (Soporta Java 17 y Jakarta EE 10)
FROM ghcr.io/eclipse-ee4j/glassfish:7.0.25

# Copiar el archivo WAR generado al directorio de autodeploy de GlassFish
COPY --from=build /app/target/Prohire.war /opt/glassfish7/glassfish/domains/domain1/autodeploy/Prohire.war

EXPOSE 8080
