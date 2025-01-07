# Build stage
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21 AS runtime

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/breeze-0.0.1-SNAPSHOT.jar breeze.jar

# Expose port 9090 for the application
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "breeze.jar"]
