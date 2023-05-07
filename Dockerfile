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
