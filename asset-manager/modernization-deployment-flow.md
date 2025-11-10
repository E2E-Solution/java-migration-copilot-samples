# Asset Manager - Modernization and Deployment to Azure Sequence Flow

This document visualizes the complete modernization and deployment journey from the legacy stack to Azure-native services.

## Overview

The modernization process transforms the Asset Manager application from:
- **From**: Java 8, Spring Boot 2.7.18, AWS S3, RabbitMQ, PostgreSQL (password-based auth)
- **To**: Java 21, Spring Boot 3.x, Azure Blob Storage, Azure Service Bus, Azure Database for PostgreSQL (managed identity)

---

## Phase 1: Assessment & Planning

```mermaid
sequenceDiagram
    actor Developer
    participant Copilot as GitHub Copilot<br/>App Modernization
    participant Assessment as Assessment Engine
    participant Report as Assessment Report
    
    Developer->>Copilot: Click "Migrate to Azure"
    activate Copilot
    Copilot->>Assessment: Analyze codebase
    activate Assessment
    
    Assessment->>Assessment: Scan dependencies<br/>(Java 8, Spring Boot 2.7.18)
    Assessment->>Assessment: Detect AWS S3 usage
    Assessment->>Assessment: Detect RabbitMQ usage
    Assessment->>Assessment: Detect PostgreSQL usage
    Assessment->>Assessment: Identify security patterns
    
    Assessment->>Report: Generate assessment report
    deactivate Assessment
    
    Report->>Developer: Display issues & solutions
    deactivate Copilot
    
    Note over Developer,Report: Review migration tasks:<br/>1. Java Version Upgrade<br/>2. PostgreSQL Migration<br/>3. S3 to Blob Storage<br/>4. RabbitMQ to Service Bus<br/>5. Health Endpoints
```

---

## Phase 2: Runtime & Framework Upgrade

```mermaid
sequenceDiagram
    actor Developer
    participant Copilot as Copilot Agent
    participant Git as Git Repository
    participant Maven as Maven Build
    participant Code as Source Code
    
    Developer->>Copilot: Run Task: Java Version Upgrade
    activate Copilot
    
    Copilot->>Git: Create feature branch<br/>(java-upgrade)
    Git-->>Copilot: Branch created
    
    Copilot->>Code: Update pom.xml<br/>Java 8 → Java 21
    Copilot->>Code: Update Spring Boot<br/>2.7.18 → 3.x
    Copilot->>Code: Update dependencies
    
    Copilot->>Maven: Build project
    activate Maven
    Maven-->>Copilot: Build result
    deactivate Maven
    
    alt Build fails
        Copilot->>Code: Fix compilation errors
        Copilot->>Maven: Rebuild
    end
    
    Copilot->>Git: Commit changes
    Copilot->>Developer: Upgrade complete
    deactivate Copilot
    
    Note over Developer,Code: Java 21 + Spring Boot 3.x ready
```

---

## Phase 3: Database Migration (PostgreSQL to Azure)

```mermaid
sequenceDiagram
    actor Developer
    participant Copilot as Copilot Agent
    participant KB as Knowledge Base
    participant Code as Source Code
    participant CVE as CVE Validator
    
    Developer->>Copilot: Run Task: Migrate to<br/>Azure Database for PostgreSQL
    activate Copilot
    
    Copilot->>KB: Fetch migration guidance
    KB-->>Copilot: PostgreSQL migration patterns
    
    Copilot->>Copilot: Generate migration plan
    Copilot->>Developer: Display plan.md
    Developer->>Copilot: Confirm plan
    
    Copilot->>Code: Add Azure Identity dependencies
    Copilot->>Code: Update application.properties<br/>(connection strings)
    Copilot->>Code: Implement managed identity<br/>authentication
    Copilot->>Code: Update repository configs
    
    Copilot->>CVE: Validate dependencies
    activate CVE
    CVE-->>Copilot: No vulnerabilities found
    deactivate CVE
    
    Copilot->>Developer: Review changes
    Developer->>Copilot: Keep changes
    deactivate Copilot
    
    Note over Developer,Code: PostgreSQL migrated to Azure<br/>with managed identity
```

---

## Phase 4: Storage Migration (S3 to Azure Blob)

```mermaid
sequenceDiagram
    actor Developer
    participant Copilot as Copilot Agent
    participant KB as Knowledge Base
    participant Code as Source Code
    participant CVE as CVE Validator
    
    Developer->>Copilot: Run Task: Migrate from<br/>AWS S3 to Azure Blob Storage
    activate Copilot
    
    Copilot->>KB: Fetch S3→Blob migration guide
    KB-->>Copilot: Migration patterns & code samples
    
    Copilot->>Copilot: Generate migration plan
    Copilot->>Developer: Display plan.md
    Developer->>Copilot: Confirm plan
    
    Copilot->>Code: Remove AWS S3 SDK dependencies
    Copilot->>Code: Add Azure Blob Storage SDK
    Copilot->>Code: Replace AwsS3Service<br/>with AzureBlobService
    Copilot->>Code: Update S3Controller<br/>to BlobController
    Copilot->>Code: Update application.properties<br/>(storage config)
    Copilot->>Code: Implement managed identity<br/>for Blob Storage
    
    Copilot->>CVE: Validate dependencies
    activate CVE
    CVE-->>Copilot: Security check passed
    deactivate CVE
    
    Copilot->>Developer: Review changes
    Developer->>Copilot: Keep changes
    deactivate Copilot
    
    Note over Developer,Code: AWS S3 replaced with<br/>Azure Blob Storage
```

---

## Phase 5: Messaging Migration (RabbitMQ to Service Bus)

```mermaid
sequenceDiagram
    actor Developer
    participant Copilot as Copilot Agent
    participant KB as Knowledge Base
    participant Code as Source Code
    participant CVE as CVE Validator
    
    Developer->>Copilot: Run Task: Migrate from<br/>RabbitMQ to Azure Service Bus
    activate Copilot
    
    Copilot->>KB: Fetch RabbitMQ→Service Bus guide
    KB-->>Copilot: Migration patterns
    
    Copilot->>Copilot: Generate migration plan
    Copilot->>Developer: Display plan.md
    Developer->>Copilot: Confirm plan
    
    Copilot->>Code: Remove Spring AMQP dependencies
    Copilot->>Code: Add Azure Service Bus SDK
    Copilot->>Code: Replace RabbitConfig<br/>with ServiceBusConfig
    Copilot->>Code: Update message producers<br/>(web module)
    Copilot->>Code: Update message consumers<br/>(worker module)
    Copilot->>Code: Implement retry logic<br/>with dead-letter queue
    Copilot->>Code: Update application.properties<br/>(messaging config)
    
    Copilot->>CVE: Validate dependencies
    activate CVE
    CVE-->>Copilot: Security check passed
    deactivate CVE
    
    Copilot->>Developer: Review changes
    Developer->>Copilot: Keep changes
    deactivate Copilot
    
    Note over Developer,Code: RabbitMQ replaced with<br/>Azure Service Bus
```

---

## Phase 6: Health Endpoints (Custom Task)

```mermaid
sequenceDiagram
    actor Developer
    participant Copilot as Copilot Agent
    participant Docs as Spring Boot Docs
    participant Code as Source Code
    
    Developer->>Copilot: Create custom task<br/>"Expose health endpoint"
    Developer->>Copilot: Add reference:<br/>Spring Boot Actuator docs
    Developer->>Copilot: Run custom task
    activate Copilot
    
    Copilot->>Docs: Fetch Spring Boot Actuator<br/>documentation
    Docs-->>Copilot: Actuator endpoints guide
    
    Copilot->>Code: Add actuator dependency<br/>(web & worker)
    Copilot->>Code: Configure health endpoints<br/>in application.properties
    Copilot->>Code: Expose /actuator/health
    Copilot->>Code: Add custom health indicators
    
    Copilot->>Developer: Review changes
    Developer->>Copilot: Keep changes
    deactivate Copilot
    
    Note over Developer,Code: Health endpoints ready<br/>for Container Apps
```

---

## Phase 7: Containerization

```mermaid
sequenceDiagram
    actor Developer
    participant Copilot as Copilot Agent
    participant Analysis as Repository Analysis
    participant Docker as Docker Build
    participant Code as Source Code
    
    Developer->>Copilot: Run Task: Containerize Application
    activate Copilot
    
    Copilot->>Analysis: Analyze workspace structure
    activate Analysis
    Analysis->>Analysis: Detect modules<br/>(web, worker)
    Analysis->>Analysis: Detect build system<br/>(Maven)
    Analysis->>Analysis: Detect language<br/>(Java 21)
    Analysis->>Analysis: Identify entry points
    Analysis-->>Copilot: Analysis complete
    deactivate Analysis
    
    Copilot->>Copilot: Generate containerization plan
    Copilot->>Developer: Display containerization-plan.copilotmd
    Developer->>Copilot: Continue
    
    Copilot->>Code: Create Dockerfile (web module)
    Note over Code: FROM eclipse-temurin:21-jre<br/>EXPOSE 8080<br/>...
    
    Copilot->>Code: Create Dockerfile (worker module)
    Note over Code: FROM eclipse-temurin:21-jre<br/>...
    
    Copilot->>Docker: Build web image
    activate Docker
    Docker-->>Copilot: Image built successfully
    deactivate Docker
    
    Copilot->>Docker: Build worker image
    activate Docker
    Docker-->>Copilot: Image built successfully
    deactivate Docker
    
    alt Build fails
        Copilot->>Code: Fix Dockerfile errors
        Copilot->>Docker: Rebuild images
    end
    
    Copilot->>Developer: Containerization complete
    deactivate Copilot
    
    Note over Developer,Docker: Docker images ready<br/>for Azure deployment
```

---

## Phase 8: Azure Deployment

```mermaid
sequenceDiagram
    actor Developer
    participant AZ as Azure CLI
    participant ACR as Azure Container<br/>Registry
    participant RG as Resource Group
    participant PostgreSQL as Azure Database<br/>for PostgreSQL
    participant Blob as Azure Blob<br/>Storage
    participant SB as Azure Service Bus
    participant CA as Azure Container Apps
    
    Developer->>AZ: az login
    Developer->>AZ: Check available regions<br/>and SKUs
    AZ-->>Developer: List regions & SKUs
    
    Developer->>AZ: Run deploy-to-azure.sh<br/>-ResourceGroupName<br/>-Location<br/>-Prefix
    activate AZ
    
    AZ->>RG: Create resource group
    RG-->>AZ: Resource group created
    
    par Provision Azure Resources
        AZ->>ACR: Create container registry
        ACR-->>AZ: Registry ready
        
        AZ->>PostgreSQL: Create PostgreSQL<br/>Flexible Server
        Note over PostgreSQL: Enable managed identity<br/>Configure firewall rules
        PostgreSQL-->>AZ: Database ready
        
        AZ->>Blob: Create Storage Account
        Note over Blob: Enable managed identity<br/>Create blob container
        Blob-->>AZ: Storage ready
        
        AZ->>SB: Create Service Bus<br/>namespace
        Note over SB: Create queues:<br/>- image-processing<br/>- image-processing.retry
        SB-->>AZ: Service Bus ready
    end
    
    AZ->>ACR: Build & push web image
    activate ACR
    ACR-->>AZ: Image pushed
    deactivate ACR
    
    AZ->>ACR: Build & push worker image
    activate ACR
    ACR-->>AZ: Image pushed
    deactivate ACR
    
    AZ->>CA: Create Container App<br/>Environment
    CA-->>AZ: Environment ready
    
    AZ->>CA: Deploy web container app
    Note over CA: Configure:<br/>- Managed identity<br/>- Environment variables<br/>- Health probes<br/>- Ingress (port 8080)
    CA-->>AZ: Web app running
    
    AZ->>CA: Deploy worker container app
    Note over CA: Configure:<br/>- Managed identity<br/>- Environment variables<br/>- Scale rules
    CA-->>AZ: Worker app running
    
    AZ->>PostgreSQL: Assign managed identity<br/>permissions
    AZ->>Blob: Assign managed identity<br/>permissions
    AZ->>SB: Assign managed identity<br/>permissions
    
    AZ->>Developer: Display web app URL
    deactivate AZ
    
    Developer->>CA: Open web app URL
    CA-->>Developer: Application running!
    
    Note over Developer,CA: Modernization complete!<br/>Azure-native, containerized,<br/>secure with managed identity
```

---

## Phase 9: Verification & Cleanup

```mermaid
sequenceDiagram
    actor Developer
    participant WebApp as Web Container App
    participant Worker as Worker Container App
    participant Blob as Azure Blob Storage
    participant SB as Azure Service Bus
    participant PostgreSQL as Azure PostgreSQL
    participant AZ as Azure CLI
    
    Developer->>WebApp: Upload image via UI
    activate WebApp
    WebApp->>Blob: Store original image<br/>(managed identity)
    WebApp->>SB: Send processing message
    WebApp->>PostgreSQL: Save metadata<br/>(managed identity)
    WebApp-->>Developer: Upload successful
    deactivate WebApp
    
    SB->>Worker: Deliver message
    activate Worker
    Worker->>Blob: Download original image
    Worker->>Worker: Generate thumbnail
    Worker->>Blob: Upload thumbnail
    Worker->>PostgreSQL: Update metadata
    Worker->>SB: Acknowledge message
    deactivate Worker
    
    Developer->>WebApp: View images
    activate WebApp
    WebApp->>PostgreSQL: Fetch metadata
    WebApp->>Blob: Retrieve images
    WebApp-->>Developer: Display image gallery
    deactivate WebApp
    
    Note over Developer,PostgreSQL: ✅ Application verified!
    
    opt Cleanup Resources
        Developer->>AZ: Run cleanup-azure-resources.sh<br/>-ResourceGroupName
        activate AZ
        AZ->>AZ: Delete resource group<br/>and all resources
        AZ-->>Developer: Cleanup complete
        deactivate AZ
    end
```

---

## Architecture Transformation Summary

### Before Modernization
```mermaid
graph LR
    A[Java 8] --> B[Spring Boot 2.7]
    B --> C[AWS S3]
    B --> D[RabbitMQ]
    B --> E[PostgreSQL]
    style A fill:#ff9999
    style C fill:#ff9999
    style D fill:#ff9999
    style E fill:#ffcc99
```

### After Modernization
```mermaid
graph LR
    A[Java 21] --> B[Spring Boot 3.x]
    B --> C[Azure Blob Storage]
    B --> D[Azure Service Bus]
    B --> E[Azure PostgreSQL]
    B --> F[Container Apps]
    C -.->|Managed Identity| G[Azure AD]
    D -.->|Managed Identity| G
    E -.->|Managed Identity| G
    style A fill:#99ff99
    style B fill:#99ff99
    style C fill:#99ccff
    style D fill:#99ccff
    style E fill:#99ccff
    style F fill:#99ccff
    style G fill:#ffff99
```

---

## Key Benefits Achieved

| Aspect | Before | After |
|--------|--------|-------|
| **Runtime** | Java 8 | Java 21 |
| **Framework** | Spring Boot 2.7.18 | Spring Boot 3.x |
| **Storage** | AWS S3 (access keys) | Azure Blob Storage (managed identity) |
| **Messaging** | RabbitMQ (password) | Azure Service Bus (managed identity) |
| **Database** | PostgreSQL (password) | Azure Database for PostgreSQL (managed identity) |
| **Deployment** | Manual/VM-based | Containerized (Azure Container Apps) |
| **Security** | Password-based auth | Managed identity + passwordless |
| **Monitoring** | None | Spring Boot Actuator health endpoints |
| **Scalability** | Limited | Auto-scaling with Container Apps |
| **Cloud-Native** | No | Yes (Azure-native services) |

---

## Migration Effort Breakdown

```mermaid
pie title Time Distribution Across Phases
    "Assessment & Planning" : 10
    "Runtime & Framework Upgrade" : 20
    "Database Migration" : 15
    "Storage Migration" : 15
    "Messaging Migration" : 15
    "Health Endpoints" : 5
    "Containerization" : 10
    "Deployment" : 10
```

---

## Next Steps

1. **Performance Testing**: Test application under load
2. **Security Hardening**: Review and enhance security policies
3. **Monitoring Setup**: Configure Azure Monitor and Application Insights
4. **CI/CD Pipeline**: Set up automated deployment pipeline
5. **Documentation**: Update technical documentation
6. **Team Training**: Train team on new Azure services

---

*Generated for Asset Manager - Java Modernization Workshop*
