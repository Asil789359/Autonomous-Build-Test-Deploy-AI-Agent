# Build Stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY common-lib ./common-lib
COPY orchestrator-service ./orchestrator-service
COPY build-service ./build-service
COPY test-service ./test-service
COPY docker-service ./docker-service
COPY infra-service ./infra-service
COPY deployment-service ./deployment-service
COPY self-healing-service ./self-healing-service
COPY api-gateway ./api-gateway

# Build common-lib first
RUN mvn install -f common-lib/pom.xml

# Build the specific service
ARG SERVICE_NAME
RUN mvn clean package -DskipTests -f ${SERVICE_NAME}/pom.xml

# Run Stage
FROM openjdk:17-jdk-slim
WORKDIR /app
ARG SERVICE_NAME
COPY --from=build /app/${SERVICE_NAME}/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
