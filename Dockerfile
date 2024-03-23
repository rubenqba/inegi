FROM observabilitystack/graalvm-maven-builder:21.0.1-ol9 AS build

WORKDIR /usr/src/app

# Copy pom.xml and download dependencies
COPY . .
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline
RUN --mount=type=cache,target=/root/.m2 ./mvnw -Pnative clean package -DskipTests

# Second stage: Lightweight debian-slim image
FROM alpine:latest

RUN apk add --no-cache libc6-compat bash curl

WORKDIR /app

# Copy the native binary from the build stage
COPY --from=build /usr/src/app/inegi-service/target/inegi-service /app/inegi-service

# Run the application
CMD ["/app/inegi-service"]