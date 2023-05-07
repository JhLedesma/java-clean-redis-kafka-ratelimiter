## Seleccionar la imagen de Java 11
#FROM openjdk:11
#
## Establecer el directorio de trabajo
#WORKDIR /app
#
## Copiar el archivo pom.xml y el wrapper de Maven al contenedor
#COPY pom.xml ./
#COPY mvnw ./
#
## Copiar el directorio .mvn al contenedor
#COPY .mvn ./.mvn
#
## Descargar las dependencias del proyecto (esto se guardará en caché, a menos que se modifique pom.xml)
#RUN ./mvnw dependency:go-offline
#
## Copiar todo el código fuente al contenedor
#COPY src ./src
#
## Empaquetar la aplicación
#RUN ./mvnw package -DskipTests
#
## Exponer el puerto 8080
#EXPOSE 8080
#
## Ejecutar la aplicación
#CMD ["java", "-jar", "./target/java-clean-redis-kafka-ratelimiter-0.0.1-SNAPSHOT.jar"]

# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk/openjdk11:alpine-slim

# Set the working directory to /app
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the application with Maven and skip tests
RUN ./mvnw package -DskipTests

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "target/app.jar"]
