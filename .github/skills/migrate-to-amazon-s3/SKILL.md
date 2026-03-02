---
name: Migrate to Amazon S3
description: Migrates local file system storage implementations in a Java/Spring Boot application to use Amazon S3 for cloud-native, stateless file storage using the AWS SDK for Java v2.
---

# AWS S3 Migration Guidelines

## What this skill helps accomplish
This skill refactors legacy Java applications that rely on local file system operations (e.g., `java.io.File`, `java.nio.file.Files`, or `FileOutputStream`) to use Amazon S3 for object storage. It ensures the application is stateless and ready for containerized deployment on AWS.

## When to use this skill
Use this skill when analyzing an application's architecture reveals local file uploads, downloads, or storage, and the target deployment environment is AWS (e.g., EKS, ECS, or EC2).

## Step-by-step procedures to follow
1. **Dependency Injection:** Analyze the project's build file (`pom.xml` or `build.gradle`). Inject the `software.amazon.awssdk:s3` dependency (AWS SDK for Java v2). Manage versions using the `software.amazon.awssdk:bom` if it is not already present.
2. **Configuration:** Create an `S3Config.java` class annotated with `@Configuration` that exposes an `S3Client` bean.
3. **Properties Externalization:** Do not hardcode bucket names or credentials. Add a placeholder property like `aws.s3.bucket-name=your-bucket-name` to `application.properties` or `application.yml` and inject it using `@Value`. 
4. **Code Transformation:** Identify services handling file operations. Replace local file reading/writing with S3 equivalent methods (`PutObjectRequest`, `GetObjectRequest`, `DeleteObjectRequest`).
5. **Test Refactoring:** Update or generate unit tests for the modified services, ensuring `S3Client` is properly mocked using Mockito.

## Examples of expected input and output

**Input (Legacy Local Storage):**
```java
public void saveFile(MultipartFile file, String filename) throws IOException {
    Path path = Paths.get("/var/local/uploads/" + filename);
    Files.write(path, file.getBytes());
}

