---
name: Migrate to Distributed Azure Cache for Redis
description: Replaces local, in-memory caching (like Ehcache or ConcurrentHashMap) with a distributed Azure Cache for Redis implementation using Spring Data Redis.
---

# Azure Cache for Redis Migration Guidelines

## What this skill helps accomplish
This skill transitions an application from stateful, in-memory caching to a stateless, distributed caching architecture using Azure Cache for Redis, allowing the application to scale horizontally safely.

## When to use this skill
Use this skill when modernizing an application that uses `@Cacheable` with a local cache manager, and the target architecture involves running multiple instances of the application behind a load balancer.

## Step-by-step procedures to follow
1. **Dependency Injection:** Add `org.springframework.boot:spring-boot-starter-data-redis` to the project. Remove legacy cache dependencies like `ehcache`.
2. **Serialization Check:** Scan the codebase for classes returned by methods annotated with `@Cacheable`. Ensure these classes implement `java.io.Serializable`.
3. **Configuration Update:** Add Redis connection properties to `application.yml` (e.g., `spring.data.redis.host`, `spring.data.redis.port`, `spring.data.redis.password`).
4. **Cache Manager Setup:** Ensure the main application class or a configuration class has the `@EnableCaching` annotation. Spring Boot will automatically auto-configure a `RedisCacheManager` when the Redis starter is present.

## Examples of expected input and output

**Input (Legacy In-Memory Entity):**
```java
public class UserProfile {
    private String id;
    private String name;
    // Getters and Setters
}

**Output (Modernized Serializable Entity for Redis):
```java
import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    // Getters and Setters
}

