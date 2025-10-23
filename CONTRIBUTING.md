# Contributing to Banking Entities API

Thank you for your interest in contributing to the Banking Entities API! This document provides guidelines and information for contributors.

## 🏗️ Architecture Overview

This project follows **Hexagonal Architecture** (Ports and Adapters) principles:

- **Domain Layer**: Core business logic and entities
- **Application Layer**: Use cases and service implementations  
- **Infrastructure Layer**: External adapters (web, database, HTTP clients)

## 🛠️ Development Setup

### Prerequisites
- Java 23 or later
- Maven 3.9+
- Git

### Local Development
```bash
# Clone the repository
git clone https://github.com/alvarofgd/banking-entities-api.git
cd banking-entities-api

# Build the project
./mvnw clean compile

# Run tests
./mvnw test

# Run the application
./mvnw spring-boot:run
```

## 🧪 Testing Guidelines

### Test Structure
- **Unit Tests**: Domain and application layer testing
- **Integration Tests**: Full application context testing
- **Component Tests**: Individual adapter testing

### Running Tests
```bash
# Run all tests
./mvnw test

# Run integration tests
./mvnw verify

# Run specific test class
./mvnw test -Dtest=BankServiceTest
```

## 📝 Code Style

### Java Conventions
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add comprehensive JavaDoc for public APIs
- Leverage Java 23 features appropriately

### Architecture Guidelines
- Keep business logic in the domain layer
- Use ports (interfaces) for external dependencies
- Implement adapters for infrastructure concerns
- Follow SOLID principles

## 🔄 Pull Request Process

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Pull Request Requirements
- [ ] All tests pass
- [ ] Code follows project conventions
- [ ] Changes are documented
- [ ] Commit messages are descriptive
- [ ] No breaking changes without discussion

## 🐛 Bug Reports

When reporting bugs, please include:
- Java version and OS
- Steps to reproduce
- Expected vs actual behavior
- Relevant logs or error messages

## 💡 Feature Requests

Feature requests should:
- Align with hexagonal architecture principles
- Include business justification
- Consider backward compatibility
- Propose implementation approach

## 📋 Code Review Checklist

- [ ] **Architecture**: Changes follow hexagonal architecture
- [ ] **Testing**: Adequate test coverage
- [ ] **Documentation**: Code is well-documented
- [ ] **Security**: No security vulnerabilities
- [ ] **Performance**: No performance regressions

## 🚀 Release Process

1. Update version in `pom.xml`
2. Update `CHANGELOG.md`
3. Create release branch
4. Test thoroughly
5. Merge to main
6. Tag release
7. Deploy to production

## 📞 Contact

- **Repository**: https://github.com/alvarofgd/banking-entities-api
- **Issues**: Use GitHub Issues for bug reports and feature requests
- **Discussions**: Use GitHub Discussions for questions and ideas

Thank you for contributing to Banking Entities API! 🏦✨