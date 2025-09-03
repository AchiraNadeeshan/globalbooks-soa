# GlobalBooks Inc. SOA & Microservices Project

## Slide 1: Title Slide

-   **Title:** GlobalBooks Inc. SOA & Microservices Project
-   **Subtitle:** A Hybrid Approach to Modernization

## Slide 2: Project Overview

-   Migration from monolithic to Hybrid SOA
-   Coexistence of SOAP and REST services
-   Focus on scalability, flexibility, and future cloud readiness

## Slide 3: Architecture Style

-   **Hybrid SOA:**
    -   SOAP Service: `CatalogService` (Legacy Partners)
    -   REST Microservices: `OrdersService`, `PaymentsService`, `ShippingService` (Modern Scalability)
-   **Integration:** RabbitMQ ESB (Asynchronous Messaging)
-   **Orchestration:** BPEL Engine (Workflow Coordination)

## Slide 4: System Integration Flow

(Diagram similar to `project_instructions.md`)

## Slide 5: Technology Stack

-   **Core:** Java (JDK 8 & 18), Spring Boot 3.x, JAX-WS
-   **Infrastructure:** PostgreSQL 17.5, RabbitMQ, Apache Tomcat 9.x, Apache ODE
-   **Security:** WS-Security, Spring Security OAuth2
-   **DevOps:** OpenTofu, GitHub Actions

## Slide 6: Service Architecture Details

-   **CatalogService (SOAP):** JAX-WS, Tomcat, WS-Security
-   **OrdersService (REST):** Spring Boot, OAuth2, PostgreSQL
-   **PaymentsService (REST):** Spring Boot, RabbitMQ Consumer
-   **ShippingService (REST):** Spring Boot, RabbitMQ Consumer

## Slide 7: BPEL Orchestration

-   `PlaceOrder` Workflow: Coordinates SOAP `CatalogService` and REST `OrdersService`
-   Mixed Protocol Handling
-   Apache ODE Deployment

## Slide 8: RabbitMQ ESB Integration

-   Asynchronous Messaging for Payments and Shipping
-   Event-Driven Architecture
-   Decoupling of Microservices

## Slide 9: Security Implementation

-   Dual Security Model: WS-Security for SOAP, OAuth2 for REST
-   Token Management & Validation
-   Cross-Protocol Security Context

## Slide 10: Local Development Setup

-   Prerequisites: JDKs, Maven, DB, RabbitMQ, Tomcat, ODE
-   `.env` configuration
-   Setup scripts

## Slide 11: Testing Strategy

-   Multi-Protocol Testing: SOAP UI, Postman
-   BPEL Process Testing
-   Integration Testing

## Slide 12: Cloud Migration Preparation (Future)

-   OpenTofu for IaC (Lambda, API Gateway, RDS, MQ)
-   Lambda Packaging Strategy
-   CI/CD Pipeline Design (GitHub Actions)

## Slide 13: Key Success Factors

-   Local Development First
-   Port Management
-   Protocol Coexistence
-   Contract-First SOAP
-   Spring Boot Configuration
-   Local Testing

## Slide 14: Q&A

-   Questions and Discussion
