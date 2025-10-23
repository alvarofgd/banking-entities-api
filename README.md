# Banking Entities API

A Spring Boot REST API for managing banking entities built with **Hexagonal Architecture** principles, developed using Java 23 and Spring Boot 3.5.6.

## Architecture Overview

This project is implemented using **Hexagonal Architecture** (also known as Ports and Adapters pattern), which provides:

- **Clear separation of concerns** between business logic and external dependencies
- **High testability** through dependency inversion
- **Flexibility** to change external adapters without affecting business logic
- **Independence** from frameworks, databases, and external services

### Hexagonal Architecture Layers

```
┌────────────────────────────────────────────────────────────────┐
│                    Infrastructure Layer                        │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │   Web Adapter   │  │ Persistence     │  │  HTTP Client    │ │
│  │  (Controllers)  │  │   Adapter       │  │    Adapter      │ │
│  │                 │  │ (Repositories)  │  │                 │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
└────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                    Application Layer                         │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │              Service Implementations                    │ │
│  │           (Use Cases & Business Logic)                  │ │
│  └─────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌────────────────────────────────────────────────────────────────┐
│                      Domain Layer                              │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │     Entities    │  │      Ports      │  │   Exceptions    │ │
│  │   (Bank Model)  │  │  (Interfaces)   │  │   (Business)    │ │
│  │                 │  │                 │  │                 │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
└────────────────────────────────────────────────────────────────┘
```

### Project Structure (Hexagonal Architecture)

```
src/main/java/com/santander/rht/bankentitiesapi/
├── domain/                          # Domain Layer (Business Core)
│   ├── model/                       # Business Entities
│   │   ├── Bank.java               # Core business entity
│   │   └── BankType.java           # Value objects
│   ├── port/                       # Ports (Interfaces)
│   │   ├── in/                     # Input ports (Use cases)
│   │   └── port/out/               # Output ports (SPI)
│   └── exception/                  # Business exceptions
│       ├── BankNotFoundException.java
│       ├── DuplicateBankException.java
│       └── InvalidBankDataException.java
├── application/                    # Application Layer
│   └── service/                    # Use case implementations
│       └── BankService.java        # Business logic
└── infrastructure/                 # Infrastructure Layer (Adapters)
    ├── web/                        # Input adapters
    │   ├── controller/             # REST controllers
    │   ├── dto/                    # Web DTOs
    │   └── mapper/                 # Web mapping
    ├── persistence/                # Output adapters
    │   ├── adapter/                # Database adapters
    │   ├── entity/                 # JPA entities
    │   ├── mapper/                 # Persistence mapping
    │   └── repository/             # JPA repositories
    ├── http/                       # External service adapters
    │   └── adapter/                # HTTP client adapters
    └── config/                     # Configuration
```

This project follows Clean Architecture principles with the following layers:

- **Domain**: Contains business logic, entities, and ports (interfaces)
- **Application**: Contains use cases and service implementations
- **Infrastructure**: Contains adapters for external systems (web, database, HTTP clients)

## Technology Stack

- **Java 23** (Application language)
- **Spring Boot 3.5.6** (Framework)
- **Spring Data JPA** (Database operations)
- **Spring WebFlux** (Reactive programming)
- **H2 Database** (for development and testing)
- **SpringDoc OpenAPI 3** (for API documentation)
- **Lombok** (for reducing Java boilerplate code)
- **MapStruct** (for object mapping)
- **JUnit 5** (for testing)
- **Maven** (build tool)

## Hexagonal Architecture Benefits

This implementation demonstrates key benefits of Hexagonal Architecture:

1. **Business Logic Isolation**: Core business logic resides in the domain layer, independent of external concerns
2. **Testability**: Easy to unit test business logic without external dependencies
3. **Flexibility**: Can easily swap implementations (e.g., from H2 to PostgreSQL, from REST to GraphQL)
4. **Dependency Inversion**: High-level modules don't depend on low-level modules
5. **Single Responsibility**: Each layer has a clear, focused responsibility

### Domain-Driven Design Elements

- **Entities**: `Bank` with business invariants and behavior
- **Value Objects**: `BankType` enum representing bank categories
- **Domain Services**: Business logic encapsulated in `BankServicePort`
- **Domain Exceptions**: Business-specific exceptions for error handling

## Getting Started

### Prerequisites

- Java 23 or later
- Maven 3.9+

### Running the Application

```bash
# Build the project
./mvnw clean compile

# Run tests
./mvnw test

# Run the application
./mvnw spring-boot:run
```

The application will start on port 8080. You can access the API documentation at:
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI spec**: http://localhost:8080/v3/api-docs

### Available Endpoints

The API follows RESTful principles and provides comprehensive bank management capabilities:

- **Bank Management**: `/api/v1/banks/`
  - `GET /api/v1/banks` - List all banks with optional filtering
  - `GET /api/v1/banks/{id}` - Get bank by ID
  - `GET /api/v1/banks/swift/{swiftCode}` - Get bank by SWIFT code
  - `POST /api/v1/banks` - Create a new bank
  - `PUT /api/v1/banks/{id}` - Update a bank
  - `DELETE /api/v1/banks/{id}` - Delete a bank
  - `GET /api/v1/banks/search` - Search banks by criteria

- **Self-Call Demonstration Endpoints**: `/api/v1/banks/self-call/`
  - `GET /api/v1/banks/self-call/{id}` - Demonstrates hexagonal architecture by calling own API via HTTP client
  - `GET /api/v1/banks/self-call/swift/{swiftCode}` - Self-call variant using SWIFT code lookup

All endpoints include comprehensive validation, error handling, and OpenAPI documentation.

#### Self-Call Endpoints Explanation

The self-call endpoints are a demonstration of **hexagonal architecture principles** in action. These endpoints show how:

1. **Output Ports**: The application defines `BankHttpClientPort` as an output port interface
2. **Output Adapters**: The `BankHttpClientAdapter` implements this port using Spring WebClient
3. **Dependency Inversion**: The business logic depends on the port interface, not the concrete HTTP implementation
4. **External Integration**: The service can call external APIs (in this case, its own endpoints) through the adapter pattern

These endpoints demonstrate how the same application can act as both:
- **API Provider**: Serving REST endpoints via web controllers
- **API Consumer**: Consuming REST endpoints via HTTP client adapters

This showcases the flexibility and testability benefits of hexagonal architecture, where external dependencies are abstracted behind port interfaces.

### Development Commands

```bash
# Build the project
./mvnw clean compile

# Run tests
./mvnw test

# Package the application
./mvnw clean package

# Run with development profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Run integration tests
./mvnw verify
```

## Java 23 Features

This project leverages modern Java 23 features including:

- **Pattern Matching**: Enhanced switch expressions and pattern matching for cleaner code
- **Records**: Immutable data carriers for DTOs and value objects
- **Text Blocks**: Improved string handling for SQL queries and JSON responses
- **Sealed Classes**: Restricted class hierarchies for better type safety
- **Virtual Threads**: Lightweight concurrency for improved performance

## Testing Strategy

The project implements comprehensive testing following hexagonal architecture principles:

- **Unit Tests**: Domain logic testing without external dependencies
- **Integration Tests**: Full application context testing with test containers
- **Component Tests**: Individual adapter testing
- **Contract Tests**: API contract validation

### Test Structure
```
src/test/java/
├── domain/model/           # Domain entity tests
├── application/service/    # Business logic tests
├── infrastructure/
│   └── web/controller/    # Web layer tests
└── integration/           # End-to-end tests
```

## Project Features

This banking entities API demonstrates a complete implementation of hexagonal architecture with:

### Core Business Features
- **CRUD Operations**: Complete bank entity management
- **SWIFT Code Validation**: Business rule enforcement
- **Duplicate Prevention**: Domain-level validation
- **Advanced Search**: Multi-criteria bank search
- **Bank Type Management**: Categorical organization
- **Self-Call Demonstration**: HTTP client integration showcasing hexagonal architecture

### Architecture Features
- **Hexagonal Architecture**: Clear separation of concerns
- **Domain-Driven Design**: Business-focused modeling
- **SOLID Principles**: Clean, maintainable code
- **Dependency Inversion**: Testable, flexible design
- **Comprehensive Error Handling**: Business-appropriate exceptions

### Technical Features
- **RESTful API Design**: Following HTTP best practices
- **OpenAPI Documentation**: Interactive API documentation
- **Reactive Programming**: Non-blocking operations with WebFlux
- **Database Abstraction**: JPA/Hibernate with H2
- **Observability**: Metrics and health checks with Spring Actuator
- **Modern Java**: Leveraging Java 23 features

## API Documentation

The API provides comprehensive documentation through OpenAPI/Swagger:

- **Interactive Documentation**: Test endpoints directly from the browser
- **Schema Definitions**: Complete request/response models
- **Error Responses**: Detailed error handling documentation
- **Authentication**: Future-ready security configuration

Access the documentation at: **http://localhost:8080/swagger-ui/index.html**

## Configuration

The application can be configured through `application.properties` file. Key configuration options include:

- **Database**: Connection settings and JPA configuration
- **Server**: Port and servlet configuration
- **Documentation**: OpenAPI and Swagger settings
- **Monitoring**: Actuator endpoints configuration
- **Application**: Business logic configuration