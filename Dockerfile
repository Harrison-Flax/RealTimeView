# Referenced from Docker Docs

# Build Stage
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests
RUN mkdir -p target/extracted && java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

# Extract Stage
FROM build AS extract
WORKDIR /app

# Final Stage
FROM eclipse-temurin:21-jre-jammy AS final

WORKDIR /app

# Copy the executable from the "package" stage
COPY --from=extract /app/target/extracted/dependencies/ ./
COPY --from=extract /app/target/extracted/spring-boot-loader/ ./
COPY --from=extract /app/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /app/target/extracted/application/ ./

# For reading the secret password file from the mounted volume
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Creating a non-privileged user to run the application
# So no root access is needed (a security risk)
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

EXPOSE 8080
ENTRYPOINT [ "/entrypoint.sh" ]