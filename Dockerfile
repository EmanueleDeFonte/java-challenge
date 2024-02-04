# Build
FROM maven:3.8.4-openjdk-8-slim AS build
COPY src /home/java-challenge/src
COPY pom.xml /home/java-challenge

RUN mvn -f /home/java-challenge/pom.xml clean package

# Copy built .jar and Run it
FROM openjdk:8-jre-slim
COPY --from=build /home/java-challenge/target/*.jar /usr/local/lib/java-challenge.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/java-challenge.jar"]
