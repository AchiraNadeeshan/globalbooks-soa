# Hybrid SOA Design

This document details the hybrid Service-Oriented Architecture (SOA) implemented for GlobalBooks Inc., combining SOAP services for legacy integration and REST microservices for modern scalability and agility.

## Core Principles

-   **Loose Coupling:** Services are designed to be independent, communicating through well-defined interfaces (WSDL for SOAP, JSON for REST) and asynchronous messaging (RabbitMQ).
-   **Service Autonomy:** Each service manages its own data and business logic, minimizing dependencies on other services.
-   **Service Reusability:** Services are built with reususability in mind, allowing them to be consumed by various clients and integrated into different business processes.
-   **Service Composability:** Services can be combined and orchestrated to form complex business workflows, as demonstrated by the BPEL `PlaceOrder` process.

## Architecture Components

1.  **SOAP CatalogService:** A traditional SOAP service exposed via WSDL, primarily for integration with legacy client systems. Deployed on Apache Tomcat.
2.  **REST Microservices (Orders, Payments, Shipping):** Independent Spring Boot applications handling specific business domains. They communicate asynchronously via RabbitMQ.
3.  **RabbitMQ ESB:** Acts as an Enterprise Service Bus (ESB) for asynchronous, event-driven communication between microservices, ensuring loose coupling and resilience.
4.  **Apache ODE BPEL Engine:** Orchestrates complex business processes, such as the `PlaceOrder` workflow, by coordinating calls between SOAP and REST services.

## Integration Patterns

-   **Request-Reply:** Synchronous communication for immediate responses (e.g., REST API calls).
-   **Event-Driven:** Asynchronous communication via message queues for decoupled processing (e.g., order creation triggering payment and shipping).
-   **Orchestration:** Centralized control of a business process flow using BPEL, coordinating multiple service interactions.

## Security Model

-   **WS-Security (SOAP):** Implemented for the `CatalogService` to secure SOAP message exchanges, typically using UsernameToken for authentication.
-   **OAuth2 (REST):** Used for securing REST microservices, providing token-based authentication and authorization for modern clients.

## Versioning Strategy

-   **URL Path Versioning:** REST APIs use `/v1/` prefix in their URLs to denote API versions, allowing for backward compatibility and controlled evolution of services.