package com.microsoft.migration.assets.config;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.identity.ChainedTokenCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.EnvironmentCredentialBuilder;
import com.azure.spring.messaging.ConsumerIdentifier;
import com.azure.spring.messaging.PropertiesSupplier;
import com.azure.spring.messaging.servicebus.core.properties.ProcessorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class AzureServiceBusConfig {
    public static final String IMAGE_PROCESSING_QUEUE = "image-processing";

    // Credential beans for different profiles
    @Bean
    @Primary
    @Profile("!local")
    public TokenCredential defaultTokenCredential() {
        // Use DefaultAzureCredential for production (includes managed identity)
        return new DefaultAzureCredentialBuilder().build();
    }
    
    @Bean
    @Primary
    @Profile("local")
    public TokenCredential localTokenCredential() {
        // For local development, use only Azure CLI and Environment credentials
        // Skip Managed Identity to avoid conflicts on Azure VMs
        return new ChainedTokenCredentialBuilder()
                .addLast(new EnvironmentCredentialBuilder().build())
                .addLast(new AzureCliCredentialBuilder().build())
                .build();
    }

    // Commenting out admin beans for local development
    // The queue already exists in Azure, so we don't need to create/manage it
    // Uncomment for production deployment in Azure where managed identity has full permissions
    
    // @Bean
    // public ServiceBusAdministrationClient adminClient(AzureServiceBusProperties properties, TokenCredential credential) {
    //     return new ServiceBusAdministrationClientBuilder()
    //         .credential(properties.getFullyQualifiedNamespace(), credential)
    //         .buildClient();
    // }


    // @Bean
    // public QueueProperties imageProcessingQueue(ServiceBusAdministrationClient adminClient) {
    //     QueueProperties queue;
    //     try {
    //         queue = adminClient.getQueue(IMAGE_PROCESSING_QUEUE);
    //     } catch (ResourceNotFoundException e) {
    //         try {
    //             queue = adminClient.createQueue(IMAGE_PROCESSING_QUEUE);
    //         } catch (ResourceExistsException ex) {
    //             // Queue was created by another instance in the meantime
    //             queue = adminClient.getQueue(IMAGE_PROCESSING_QUEUE);
    //         }
    //     }
    //     return queue;
    // }

    @Bean
    public PropertiesSupplier<ConsumerIdentifier, ProcessorProperties> propertiesSupplier() {
        return identifier -> {
            ProcessorProperties processorProperties = new ProcessorProperties();
            processorProperties.setAutoComplete(false);
            return processorProperties;
        };
    }
}
