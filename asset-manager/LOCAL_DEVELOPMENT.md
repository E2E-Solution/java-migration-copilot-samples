# Running Assets Manager Locally

This guide explains how to run the Assets Manager application on your local machine.

## Prerequisites

- **JDK 21** installed at `/home/sithukyaw/.jdk/jdk-21.0.8` (or update paths in scripts)
- **Docker** (for local PostgreSQL database)
- **Maven** (included via mvnw wrapper)
- **Azure CLI** (optional, for Azure service authentication)

## Option 1: Local Development Mode (Recommended for Testing)

This mode uses local file storage and a local PostgreSQL database. **No Azure resources required!**

### Quick Start

1. **Start the application:**
   ```bash
   ./run-local-dev.sh
   ```

2. **Access the web application:**
   - Open browser: http://localhost:8080

3. **What happens:**
   - PostgreSQL database starts in Docker container
   - Web app uses local file storage (./uploads directory)
   - Files are stored locally instead of Azure Blob Storage
   - Messages are logged instead of sent to Service Bus

### Manual Setup

If you prefer to run components separately:

```bash
# 1. Start PostgreSQL
docker compose -f docker-compose-dev.yml up -d

# 2. Create uploads directory
mkdir -p uploads

# 3. Start web application
JAVA_HOME=/home/sithukyaw/.jdk/jdk-21.0.8 \
  ./mvnw spring-boot:run -pl web -Dspring-boot.run.profiles=dev

# 4. (Optional) Start worker in another terminal
JAVA_HOME=/home/sithukyaw/.jdk/jdk-21.0.8 \
  ./mvnw spring-boot:run -pl worker -Dspring-boot.run.profiles=dev
```

### Stop Services

```bash
# Stop web app: Ctrl+C in terminal

# Stop PostgreSQL
docker compose -f docker-compose-dev.yml down
```

---

## Option 2: Connect to Azure Services

This mode connects to **real Azure resources** (Storage Account, Service Bus, PostgreSQL).

### Prerequisites

You need existing Azure resources:
- Azure Storage Account with blob container
- Azure Service Bus with queue named "image-processing"
- Azure Database for PostgreSQL

### Setup

1. **Set environment variables:**

   ```bash
   export AZURE_STORAGE_ACCOUNT_NAME="your-storage-account"
   export AZURE_STORAGE_BLOB_CONTAINER_NAME="images"
   export AZURE_SERVICEBUS_NAMESPACE="your-servicebus-namespace"
   export AZURE_POSTGRES_SERVER="your-server"
   export AZURE_POSTGRES_DB="assets_manager"
   export AZURE_POSTGRES_USER="your-username"
   export AZURE_POSTGRES_PASSWORD="your-password"
   ```

2. **Login to Azure (for authentication):**
   ```bash
   az login
   ```

3. **Start the application:**
   ```bash
   ./run-local-azure.sh
   ```

### Alternative: Using Connection Strings

For Service Bus, you can use connection string instead of DefaultAzureCredential:

```bash
export AZURE_SERVICEBUS_CONNECTION_STRING="Endpoint=sb://your-namespace.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=your-key"
```

Then uncomment this line in `application-local.properties`:
```properties
spring.cloud.azure.servicebus.connection-string=${AZURE_SERVICEBUS_CONNECTION_STRING}
```

---

## Using Azurite (Azure Storage Emulator)

For local Azure Blob Storage emulation:

1. **Install and start Azurite:**
   ```bash
   docker run -p 10000:10000 -p 10001:10001 -p 10002:10002 \
     mcr.microsoft.com/azure-storage/azurite
   ```

2. **Update environment variables:**
   ```bash
   export AZURE_STORAGE_ACCOUNT_NAME="devstoreaccount1"
   export AZURE_STORAGE_BLOB_CONTAINER_NAME="images"
   ```

3. **Update `application-local.properties` to use Azurite endpoint:**
   ```properties
   azure.storage.blob.endpoint=http://127.0.0.1:10000/devstoreaccount1
   ```

---

## Troubleshooting

### Port Already in Use
- **PostgreSQL (5432):** Stop other PostgreSQL instances or change port in `docker-compose-dev.yml`
- **Web App (8080):** Change port with `--server.port=8081`

### Database Connection Issues
```bash
# Check if PostgreSQL is running
docker ps | grep postgres

# View PostgreSQL logs
docker logs assets-manager-postgres-dev
```

### Azure Authentication Issues
```bash
# Verify Azure login
az account show

# Login again
az login
```

### Build Issues
```bash
# Clean and rebuild
JAVA_HOME=/home/sithukyaw/.jdk/jdk-21.0.8 ./mvnw clean install -DskipTests
```

---

## Configuration Files

- **Dev Profile:** `application-dev.properties` (local file storage)
- **Local Profile:** `application-local.properties` (Azure services)
- **Production:** `application.properties` (Container Apps with managed identity)

---

## Testing the Application

1. **Upload an image:**
   - Go to http://localhost:8080
   - Click "Upload" and select an image file

2. **View images:**
   - Images appear in the list view
   - Click to view original or thumbnail

3. **Check logs:**
   - Web app logs show upload activity
   - Worker logs show thumbnail generation (if running)

---

## Development Tips

- Use `dev` profile for rapid development without Azure dependencies
- Use `local` profile to test integration with Azure services before deployment
- Check actuator endpoints: http://localhost:8080/actuator/health
