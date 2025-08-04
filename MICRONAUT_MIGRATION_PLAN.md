# Sparklet Backend: Spring Boot to Micronaut Migration Plan

## Overview

This document outlines the step-by-step plan for migrating the Sparklet backend from Spring Boot to Micronaut. The migration is designed to be educational, allowing you to learn Micronaut concepts while refactoring a real application.

## Current Spring Boot Stack

- **Framework:** Spring Boot 3.2.1
- **Security:** Spring Security with JWT
- **Build Tool:** Maven
- **Java Version:** 17
- **Key Dependencies:**
  - spring-boot-starter-web
  - spring-boot-starter-security
  - spring-boot-starter-validation
  - jjwt (JSON Web Tokens)

## Target Micronaut Stack

- **Framework:** Micronaut 4.x
- **Security:** Micronaut Security JWT
- **Build Tool:** Maven
- **Java Version:** 17
- **Key Dependencies:**
  - micronaut-http-server-netty
  - micronaut-security-jwt
  - micronaut-validation

---

## Migration Phases

### Phase 1: Project Setup & Dependencies (Foundation)

#### Step 1.1: Create New Micronaut Project Structure
```
sparklet-backend-micronaut/
├── pom.xml
├── micronaut-cli.yml
├── src/
│   ├── main/
│   │   ├── java/com/sparklet/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── logback.xml
│   └── test/java/com/sparklet/
└── Dockerfile (optional)
```

#### Step 1.2: Dependency Mapping

| Spring Boot | Micronaut | Purpose |
|-------------|-----------|---------|
| `spring-boot-starter-web` | `micronaut-http-server-netty` | HTTP server |
| `spring-boot-starter-security` | `micronaut-security-jwt` | Security & JWT |
| `spring-boot-starter-validation` | `micronaut-validation` | Bean validation |
| `spring-boot-starter-test` | `micronaut-test-junit5` | Testing |
| `spring-boot-devtools` | `micronaut-runtime` | Development |

#### Step 1.3: New pom.xml Structure
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.sparklet</groupId>
    <artifactId>sparklet-backend-micronaut</artifactId>
    <version>0.1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <parent>
        <groupId>io.micronaut.platform</groupId>
        <artifactId>micronaut-parent</artifactId>
        <version>4.2.4</version>
    </parent>

    <properties>
        <packaging>jar</packaging>
        <jdk.version>17</jdk.version>
        <release.version>17</release.version>
        <micronaut.version>4.2.4</micronaut.version>
        <micronaut.runtime>netty</micronaut.runtime>
    </properties>
    
    <!-- Dependencies section with Micronaut BOM -->
</project>
```

#### **Learning Goals:**
- Understand Micronaut's compile-time dependency injection
- Learn about Micronaut's lighter dependency footprint
- Compare startup performance differences

---

### Phase 2: Configuration Migration

#### Step 2.1: application.properties → application.yml

**Current Spring configuration:**
```properties
server.port=8080
cors.allowed-origins=http://localhost:5173,http://localhost:5174,http://localhost:3000
jwt.secret=sparkletSecretKey...
jwt.expiration=86400000
```

**Target Micronaut configuration:**
```yaml
micronaut:
  application:
    name: sparklet-backend
  server:
    port: 8080
    cors:
      enabled: true
      configurations:
        all:
          allowed-origins:
            - http://localhost:5173
            - http://localhost:5174
            - http://localhost:3000
          allowed-methods:
            - GET
            - POST
            - PUT
            - DELETE
            - OPTIONS
          allowed-headers:
            - "*"
          allow-credentials: true
  security:
    authentication: bearer
    token:
      jwt:
        signatures:
          secret:
            generator:
              secret: "${JWT_SECRET:sparkletSecretKey...}"
        generator:
          access-token:
            expiration: 86400
```

#### Step 2.2: Security Configuration Transformation

**Spring Security (WebSecurityConfig.java) → Micronaut Security (application.yml + minimal Java)**

Instead of the large `WebSecurityConfig` class, Micronaut uses:
1. YAML configuration for most security rules
2. Small, focused `@Factory` classes for custom beans
3. Security rules defined declaratively

#### **Learning Goals:**
- Configuration-over-code approach
- YAML-first configuration philosophy
- Understand how Micronaut reduces boilerplate

---

### Phase 3: Security & Authentication Infrastructure

#### Step 3.1: JWT Security Components Migration

| Spring Component | Micronaut Equivalent | Migration Complexity |
|------------------|---------------------|---------------------|
| `WebSecurityConfig` | `application.yml` + `SecurityConfiguration` | High - Complete rethink |
| `AuthTokenFilter` | `TokenAuthenticationFetcher` | Medium - Different interface |
| `AuthEntryPointJwt` | `AuthenticationFailureHandler` | Low - Similar concept |
| `JwtUtils` | `JwtClaimsSetGenerator` | Medium - Different API |
| `UserDetailsService` | `AuthenticationProvider` | Medium - Different contract |

#### Step 3.2: Security Rules Migration

**Spring Security Chain:**
```java
.authorizeHttpRequests(authz -> authz
    .requestMatchers("/api/auth/**").permitAll()
    .requestMatchers("/api/test/**").permitAll()
    .requestMatchers("/api", "/api/").permitAll()
    .anyRequest().authenticated()
);
```

**Micronaut Security Rules:**
```yaml
micronaut:
  security:
    intercept-url-map:
      - pattern: /api/auth/**
        access:
          - isAnonymous()
      - pattern: /api/test/**
        access:
          - isAnonymous()
      - pattern: /api
        access:
          - isAnonymous()
      - pattern: /**
        access:
          - isAuthenticated()
```

#### Step 3.3: Custom Security Components

**AuthenticationProvider Implementation:**
```java
@Singleton
public class UserAuthenticationProvider implements AuthenticationProvider {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserAuthenticationProvider(UserService userService, 
                                    PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(
            @Nullable HttpRequest<?> httpRequest, 
            AuthenticationRequest<?, ?> authenticationRequest) {
        // Implementation
    }
}
```

#### **Learning Goals:**
- Micronaut's reactive security model
- Publisher-based authentication
- Compile-time security configuration validation

---

### Phase 4: Controllers Migration (API Layer)

#### Step 4.1: Annotation Mapping Reference

| Spring | Micronaut | Notes |
|--------|-----------|-------|
| `@RestController` | `@Controller` | Micronaut is more explicit |
| `@RequestMapping("/api/auth")` | `@Controller("/api/auth")` | Same path mapping |
| `@PostMapping("/login")` | `@Post("/login")` | Shorter annotations |
| `@GetMapping` | `@Get` | Consistent pattern |
| `@RequestBody` | `@Body` | Shorter name |
| `@PathVariable` | `@PathVariable` | Same annotation |
| `@RequestParam` | `@QueryValue` | More descriptive name |
| `@Autowired` | `@Inject` (constructor) | Constructor injection preferred |

#### Step 4.2: Controller Migration Order

1. **TestController** (Simplest - good learning start)
   - Basic GET/POST endpoints
   - No complex security
   - Minimal dependencies

2. **ApiController** (Medium complexity)
   - Basic CRUD operations
   - Simple authentication requirements

3. **AuthController** (Most complex)
   - JWT token generation
   - User registration/login
   - Complex security interactions

4. **Remaining Controllers** (Standard patterns)
   - DashboardController
   - FeedbackController
   - InterviewController
   - JobApplicationController
   - PageController
   - PaymentController
   - ProblemController
   - UserController

#### Step 4.3: Example Controller Migration

**Spring AuthController (Before):**
```java
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Implementation
    }
}
```

**Micronaut AuthController (After):**
```java
@Controller("/api/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    @Post("/login")
    public HttpResponse<?> authenticateUser(@Valid @Body LoginRequest loginRequest) {
        // Implementation
    }
}
```

#### **Learning Goals:**
- Constructor-based dependency injection
- Micronaut's HTTP response model
- Reactive programming patterns (optional)

---

### Phase 5: Service Layer & Business Logic

#### Step 5.1: Service Class Migration

**Spring Services:**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
}
```

**Micronaut Services:**
```java
@Singleton
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
}
```

#### Step 5.2: Method-Level Security

**Spring:**
```java
@PreAuthorize("hasRole('ADMIN')")
public List<User> getAllUsers() {
    return userRepository.findAll();
}
```

**Micronaut:**
```java
@Secured("ROLE_ADMIN")
public List<User> getAllUsers() {
    return userRepository.findAll();
}
```

#### Step 5.3: Data Access Layer

If your project uses Spring Data JPA:
- **Spring Data JPA** → **Micronaut Data JPA**
- Repository interfaces remain mostly the same
- Some query method naming conventions might differ

#### **Learning Goals:**
- Singleton vs Service scoping
- Compile-time dependency injection benefits
- Method security differences

---

### Phase 6: DTOs and Models

#### Step 6.1: Minimal Changes Required

**Good News:** DTOs and model classes require minimal changes:
- Validation annotations (`@Valid`, `@NotNull`, etc.) work the same
- Jackson annotations for JSON serialization remain unchanged
- JPA annotations (if used) remain the same

#### Step 6.2: Serialization Configuration

**Spring Boot Auto-configuration → Micronaut Explicit Configuration**

```yaml
jackson:
  serialization:
    write-dates-as-timestamps: false
  deserialization:
    fail-on-unknown-properties: false
```

#### **Learning Goals:**
- Micronaut's explicit configuration philosophy
- JSON serialization customization

---

### Phase 7: Testing Migration

#### Step 7.1: Test Framework Changes

| Spring Boot Testing | Micronaut Testing | Migration Effort |
|---------------------|-------------------|------------------|
| `@SpringBootTest` | `@MicronautTest` | Low - annotation swap |
| `MockMvc` | `HttpClient` | Medium - different API |
| `@MockBean` | `@MockBean` | Low - same concept |
| `TestRestTemplate` | `HttpClient` | Medium - different approach |

#### Step 7.2: Example Test Migration

**Spring Test (Before):**
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void testLogin() {
        ResponseEntity<String> response = restTemplate.postForEntity(
            "/api/auth/login", loginRequest, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
```

**Micronaut Test (After):**
```java
@MicronautTest
class AuthControllerTest {
    @Inject
    @Client("/")
    HttpClient client;
    
    @Test
    void testLogin() {
        HttpResponse<String> response = client.toBlocking().exchange(
            HttpRequest.POST("/api/auth/login", loginRequest), String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
    }
}
```

#### **Learning Goals:**
- Micronaut's faster test startup
- Different HTTP client testing approach
- Compile-time test optimization

---

### Phase 8: Build & Deployment

#### Step 8.1: Maven Plugin Configuration

**Key Micronaut Maven Plugins:**
```xml
<plugin>
    <groupId>io.micronaut.maven</groupId>
    <artifactId>micronaut-maven-plugin</artifactId>
    <configuration>
        <configFile>aot-${packaging}.properties</configFile>
    </configuration>
</plugin>
```

#### Step 8.2: Docker Configuration

**Micronaut Docker Benefits:**
- Smaller image sizes
- Faster startup times
- Native image compilation support

#### Step 8.3: Performance Optimization Options

1. **Standard JVM Mode:**
   - Faster startup than Spring Boot
   - Lower memory usage

2. **GraalVM Native Image:**
   - Sub-second startup times
   - Minimal memory footprint
   - Perfect for serverless deployments

#### **Learning Goals:**
- Micronaut's AOT (Ahead of Time) compilation
- Docker optimization strategies
- Native image compilation benefits

---

## Migration Timeline & Effort Estimation

### Recommended Migration Order

1. **Week 1: Foundation (Phases 1-2)**
   - Set up new Micronaut project
   - Migrate configuration
   - **Effort:** 8-12 hours
   - **Learning Focus:** Project structure, configuration philosophy

2. **Week 2: Security (Phase 3)**
   - Implement authentication
   - Migrate JWT handling
   - **Effort:** 12-16 hours
   - **Learning Focus:** Micronaut Security model, reactive patterns

3. **Week 3: Controllers (Phase 4)**
   - Start with TestController
   - Move to simpler controllers
   - End with AuthController
   - **Effort:** 16-20 hours
   - **Learning Focus:** HTTP handling, dependency injection

4. **Week 4: Services & Testing (Phases 5-7)**
   - Migrate service layer
   - Set up testing framework
   - **Effort:** 12-16 hours
   - **Learning Focus:** Service patterns, testing approaches

5. **Week 5: Polish & Deploy (Phase 8)**
   - Performance tuning
   - Documentation
   - Deployment setup
   - **Effort:** 8-12 hours
   - **Learning Focus:** Optimization, deployment strategies

### Total Estimated Effort: 56-76 hours (7-10 working days)

---

## Key Learning Outcomes

By the end of this migration, you will understand:

### 1. **Compile-Time vs Runtime DI**
- How Micronaut eliminates reflection
- Performance benefits of compile-time DI
- Debugging advantages

### 2. **Configuration Philosophy**
- YAML-first configuration
- Reduced Java boilerplate
- Environment-specific configurations

### 3. **Security Model Differences**
- Reactive security patterns
- JWT handling approaches
- Method-level security

### 4. **HTTP Handling**
- Netty-based server performance
- Reactive programming benefits
- Client testing patterns

### 5. **Modern Java Practices**
- Constructor injection patterns
- Immutable configurations
- Functional programming integration

---

## Comparison: Before vs After

### Performance Metrics (Expected)
| Metric | Spring Boot | Micronaut | Improvement |
|--------|-------------|-----------|-------------|
| Startup Time | 3-5 seconds | 1-2 seconds | 50-70% faster |
| Memory Usage | 150-200MB | 80-120MB | 30-40% less |
| Request Latency | 5-10ms | 3-7ms | 20-30% faster |
| JAR Size | 35-45MB | 15-25MB | 40-50% smaller |

### Code Complexity
| Aspect | Spring Boot | Micronaut | Change |
|--------|-------------|-----------|---------|
| Configuration Lines | ~100 (WebSecurityConfig) | ~30 (application.yml) | 70% reduction |
| Annotations | @RestController, @RequestMapping | @Controller | Simplified |
| Dependencies | Field injection | Constructor injection | More explicit |
| Testing Setup | MockMvc setup | HttpClient | Cleaner API |

---

## Rollback Plan

### If Migration Needs to be Reversed:
1. **Keep Original Spring Boot Project** - Don't delete until migration is complete
2. **Feature Flag Toggle** - Run both backends in parallel during transition
3. **Database Compatibility** - Ensure both frameworks can use same database
4. **API Compatibility** - Maintain same REST API contracts

---

## Next Steps

### Phase 1 Ready Commands:
```bash
# Generate new Micronaut project
mn create-app com.sparklet.sparklet-backend-micronaut --features=security-jwt,validation,http-client

# Navigate to project
cd sparklet-backend-micronaut

# Start development
./mvnw mn:run
```

### Ready to Start?
When you're ready to begin, we'll start with Phase 1: creating the new Micronaut project structure and understanding the basic differences between Spring Boot and Micronaut dependency injection.

---

*This migration plan is designed to be educational - each phase builds upon the previous one, teaching you Micronaut concepts while accomplishing real migration work.*
