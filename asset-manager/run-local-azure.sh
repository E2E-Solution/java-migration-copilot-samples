#!/bin/bash

echo "=========================================="
echo "Starting Assets Manager with Azure Services"
echo "=========================================="
echo ""
echo "This script connects to REAL Azure services."
echo "Make sure you have set the following environment variables:"
echo ""
echo "Required:"
echo "  AZURE_STORAGE_ACCOUNT_NAME=your-storage-account"
echo "  AZURE_STORAGE_BLOB_CONTAINER_NAME=images"
echo "  AZURE_SERVICEBUS_NAMESPACE=your-servicebus-namespace"
echo "  AZURE_POSTGRES_SERVER=your-server"
echo "  AZURE_POSTGRES_DB=assets_manager"
echo "  AZURE_POSTGRES_USER=your-username"
echo "  AZURE_POSTGRES_PASSWORD=your-password"
echo ""
echo "Optional (for easier Service Bus connection):"
echo "  AZURE_SERVICEBUS_CONNECTION_STRING=Endpoint=sb://..."
echo ""
echo "=========================================="
echo ""

# Check if required environment variables are set
if [ -z "$AZURE_STORAGE_ACCOUNT_NAME" ] || [ -z "$AZURE_SERVICEBUS_NAMESPACE" ] || [ -z "$AZURE_POSTGRES_SERVER" ]; then
    echo "ERROR: Required environment variables are not set!"
    echo "Please export the required variables before running this script."
    exit 1
fi

echo "Environment variables detected:"
echo "  Storage Account: $AZURE_STORAGE_ACCOUNT_NAME"
echo "  Service Bus: $AZURE_SERVICEBUS_NAMESPACE"
echo "  PostgreSQL: $AZURE_POSTGRES_SERVER"
echo ""

# Optional: Login to Azure for authentication
echo "Logging in to Azure (for DefaultAzureCredential)..."
az login --output none 2>/dev/null || echo "Warning: Azure login failed or az CLI not available"
echo ""

echo "=========================================="
echo "Starting Web Application..."
echo "=========================================="
echo "Web app will be available at: http://localhost:8080"
echo ""

# Start web application with local profile
JAVA_HOME=/home/sithukyaw/.jdk/jdk-21.0.8 \
  ./mvnw spring-boot:run -pl web -Dspring-boot.run.profiles=local

# Note: Worker can be started separately with:
# JAVA_HOME=/home/sithukyaw/.jdk/jdk-21.0.8 ./mvnw spring-boot:run -pl worker -Dspring-boot.run.profiles=local
