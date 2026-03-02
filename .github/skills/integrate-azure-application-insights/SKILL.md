---
name: Integrate Azure Application Insights
description: Upgrades local file-based logging to distributed, cloud-native telemetry by configuring Azure Application Insights.
---

# Azure Application Insights Integration Guidelines

## What this skill helps accomplish
This skill transitions legacy logging mechanisms to use Azure Application Insights. It ensures logs, distributed traces, and metrics are sent to Azure Monitor for centralized observability.

## When to use this skill
Use this skill when containerizing an application, as local log files (`.log`) are ephemeral and lost when containers restart. 

## Step-by-step procedures to follow
1. **Dependency Injection:** Add the `com.azure.spring:spring-cloud-azure-starter-monitor` dependency to the project.
2. **Configuration Update:** Add the Application Insights connection string property placeholder to `application.yml`: `applicationinsights.connection.string=${APPLICATIONINSIGHTS_CONNECTION_STRING}`.
3. **Logback Transformation:** If `logback-spring.xml` or `logback.xml` exists, remove local `FileAppender` configurations. Ensure a standard `ConsoleAppender` is configured (Application Insights automatically captures console output and SLF4J logs in Spring Boot).

## Examples of expected input and output

**Input (Legacy Logback Config):**
```xml
<appender name="FILE" class="ch.qos.logback.core.FileAppender">
    <file>/var/log/myapp.log</file>
    <encoder>
        <pattern>%d [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
</appender>

**Output (Modernized Logback Config):
```xml
<appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
        <pattern>%d [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
</appender>

