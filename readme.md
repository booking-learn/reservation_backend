#  VetClinic API


REST API for veterinary clinic management — JWT-secured, built with hexagonal architecture.

📄 **[Explore the API on Swagger UI](https://vet-clinic-backend-eji8.onrender.com/swagger-ui/index.html)**

> ⚠️ Hosted on a free Render instance — first request may take 3 minutes if the service is idle.

---

## Stack

`Java 25` · `Spring Boot 4.1` · `Spring Security + JWT` · `JPA / Hibernate 7` · `TiDB Cloud (MySQL)` · `Flyway` · `Docker` · `GitHub Actions` 
· `Testcontainers` · `JUnit 5` · `MockMvc`

## Architecture

Hexagonal (Ports & Adapters) — domain layer fully decoupled from infrastructure.

```
domain/          → entities, business rules
application/     → use cases, ports
api/             → REST controllers, DTOs
infrastructure/  → JPA, security, external adapters
```

## Testing

Unit tests with **JUnit 5 + Mockito**, integration tests with **Testcontainers** (real MySQL container, no mocks) and E2E tests with **MockMvc**
