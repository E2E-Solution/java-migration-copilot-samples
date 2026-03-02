---
name: Implement Azure Managed Identity (Passwordless Auth)
description: Replaces traditional connection strings and password-based authentication for Azure services with passwordless Entra ID authentication using `DefaultAzureCredential`.
---

# Azure Managed Identity Migration Guidelines

## What this skill helps accomplish
This skill refactors Azure client configurations (e.g., Blob Storage, Service Bus, Azure SQL) to stop using connection strings containing access keys or passwords. It implements `DefaultAzureCredential` to seamlessly use Managed Identities in Azure environments.

## When to use this skill
Use this skill when moving from local development or legacy credential management to a secure, passwordless Azure deployment (App Service, AKS, or Container Apps).

## Step-by-step procedures to follow
1. **Dependency Injection:** Ensure `com.azure:azure-identity` is present in the build file.
2. **Code Transformation:** Locate configuration beans creating Azure service clients (e.g., `BlobServiceClientBuilder`, `ServiceBusClientBuilder`).
3. **Refactor Builders:** Remove `.connectionString()` methods. Replace them with endpoint URLs and inject `.credential(new DefaultAzureCredentialBuilder().build())`.
4. **Clean Up:** Remove the old connection string properties from the application configuration files, replacing them with standard endpoint URIs.

## Examples of expected input and output

**Input (Legacy Password Auth):**
```java
@Bean
public BlobServiceClient blobServiceClient(@Value("${azure.storage.connection-string}") String connectionString) {
    return new BlobServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient();
}

**Output (Modernized Managed Identity Auth):
```java
@Bean
public BlobServiceClient blobServiceClient(@Value("${azure.storage.endpoint}") String endpoint) {
    return new BlobServiceClientBuilder()
            .endpoint(endpoint)
            .credential(new DefaultAzureCredentialBuilder().build())
            .buildClient();
}

