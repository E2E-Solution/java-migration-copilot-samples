---
name: Externalize Secrets to Azure Key Vault
description: Migrates hardcoded sensitive data and local configuration credentials in a Spring Boot application to Azure Key Vault using the Spring Cloud Azure integration.
---

# Azure Key Vault Migration Guidelines

## What this skill helps accomplish
This skill scans for hardcoded secrets (like database passwords, API keys, and SAS tokens) within `application.properties`, `application.yml`, and Java classes. It replaces them with references to Azure Key Vault, injecting the necessary Spring Cloud Azure dependencies.

## When to use this skill
Use this skill when preparing an application for production deployment on Azure to ensure compliance with zero-trust security models and to remove plain-text secrets from the source code.

## Step-by-step procedures to follow
1. **Dependency Injection:** Add `com.azure.spring:spring-cloud-azure-starter-keyvault-secrets` to the `pom.xml` or `build.gradle`. Manage versions via the `spring-cloud-azure-dependencies` BOM.
2. **Configuration Update:** Remove hardcoded passwords from `application.yml` or `application.properties`. 
3. **Add Key Vault Properties:** Add the Spring Cloud Azure Key Vault endpoint configuration property: `spring.cloud.azure.keyvault.secret.property-sources[0].endpoint=${KEY_VAULT_URI}`.
4. **Code Transformation:** Ensure any classes requiring these secrets use the `@Value("${secret-name}")` annotation, allowing Spring to dynamically fetch them from Key Vault at runtime.

## Examples of expected input and output

**Input (Legacy Config):**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://[myserver.postgres.database.azure.com:5432/mydb](https://myserver.postgres.database.azure.com:5432/mydb)
    username: adminuser
    password: SuperSecretPassword123!

Output (Modernized Config):
spring:
  cloud:
    azure:
      keyvault:
        secret:
          property-sources:
            - endpoint: ${AZURE_KEYVAULT_ENDPOINT}
  datasource:
    url: jdbc:postgresql://[myserver.postgres.database.azure.com:5432/mydb](https://myserver.postgres.database.azure.com:5432/mydb)
    username: adminuser
    password: ${db-password} # Fetched automatically from Key Vault

