#!/bin/bash

echo "=========================================="
echo "Starting Assets Manager in DEV mode"
echo "=========================================="

# Start PostgreSQL with Docker
echo "Starting PostgreSQL..."
docker run -d \
  --name assets-manager-postgres-dev \
  -e POSTGRES_DB=assets_manager_dev \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:16-alpine 2>/dev/null || echo "PostgreSQL container already running or failed to start"

# Wait for PostgreSQL to be ready
echo "Waiting for PostgreSQL to be ready..."
sleep 5

# Create uploads directory
mkdir -p uploads

echo ""
echo "=========================================="
echo "Starting Web Application..."
echo "=========================================="
echo "Web app will be available at: http://localhost:8080"
echo ""

# Start web application with dev profile
JAVA_HOME=/home/sithukyaw/.jdk/jdk-21.0.8 \
  ./mvnw spring-boot:run -pl web -Dspring-boot.run.profiles=dev

# Note: Worker can be started separately with:
# JAVA_HOME=/home/sithukyaw/.jdk/jdk-21.0.8 ./mvnw spring-boot:run -pl worker -Dspring-boot.run.profiles=dev
