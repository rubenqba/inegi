# First stage: JDK with GraalVM
FROM observabilitystack/graalvm-maven-builder:21.0.1-ol9 AS build
#FROM ghcr.io/graalvm/jdk:22 AS build

# Update package lists and Install Maven
#RUN microdnf update -y && \
#    microdnf install -y gcc glibc-devel zlib-devel libstdc++-devel gcc-c++ && \
#    microdnf clean all

WORKDIR /usr/src/app

# Copy pom.xml and download dependencies
COPY . .
RUN ./mvnw dependency:go-offline
RUN java --version
RUN ./mvnw -Pnative clean package -DskipTests

# Second stage: Lightweight debian-slim image
FROM debian:bookworm-slim

WORKDIR /app

# Copy the native binary from the build stage
COPY --from=build /usr/src/app/inegi-service/target/inegi-service /app/inegi-service

# Run the application
CMD ["/app/inegi-service"]