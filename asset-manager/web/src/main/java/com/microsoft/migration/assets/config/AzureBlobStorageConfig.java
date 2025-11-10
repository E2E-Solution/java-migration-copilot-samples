package com.microsoft.migration.assets.config;

import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.identity.ChainedTokenCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.EnvironmentCredentialBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AzureBlobStorageConfig {

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Bean
    @Profile("!local")
    public BlobServiceClient blobServiceClient() {
        // Use DefaultAzureCredential for production (includes managed identity)
        return new BlobServiceClientBuilder()
                .endpoint("https://" + accountName + ".blob.core.windows.net")
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }
    
    @Bean
    @Profile("local")
    public BlobServiceClient blobServiceClientLocal() {
        // For local development, use only Azure CLI and Environment credentials
        // Skip Managed Identity to avoid conflicts on Azure VMs
        return new BlobServiceClientBuilder()
                .endpoint("https://" + accountName + ".blob.core.windows.net")
                .credential(new ChainedTokenCredentialBuilder()
                        .addLast(new EnvironmentCredentialBuilder().build())
                        .addLast(new AzureCliCredentialBuilder().build())
                        .build())
                .buildClient();
    }
}
