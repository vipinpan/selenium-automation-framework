# Multi-stage build for Selenium automation framework
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first for better Docker layer caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Production stage
FROM selenium/standalone-chrome:latest

# Install Java and Maven
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven \
    wget \
    curl \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy built artifacts from build stage
COPY --from=build /app/target ./target
COPY --from=build /app/pom.xml .
COPY --from=build /app/src ./src

# Create directories for reports and logs
RUN mkdir -p /app/target/reports /app/target/screenshots /app/target/logs

# Set environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin

# Expose ports for VNC and Selenium
EXPOSE 4444 5900 7900

# Default command to run tests
CMD ["mvn", "test"]
