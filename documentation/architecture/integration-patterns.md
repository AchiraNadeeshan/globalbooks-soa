# Integration Patterns

This document describes the key integration patterns employed in the GlobalBooks Inc. hybrid SOA to facilitate communication and coordination between diverse services.

## 1. Request-Reply (Synchronous Communication)

-   **Description:** A fundamental pattern where a client sends a request to a service and synchronously waits for a response. This is suitable for operations requiring immediate feedback.
-   **Application:**
    -   **REST Microservices:** All direct REST API calls (e.g., a client calling `OrdersService` to create an order) follow this pattern.
    -   **BPEL to CatalogService:** The BPEL orchestration engine makes a synchronous SOAP call to the `CatalogService` to retrieve book details.
-   **Technologies:** HTTP/S, RESTful APIs, SOAP.

## 2. Event-Driven Architecture (Asynchronous Messaging)

-   **Description:** Services communicate by publishing and consuming events via a message broker. This decouples services, improves scalability, and enhances resilience by allowing services to operate independently.
-   **Application:**
    -   **OrdersService to Payments/ShippingService:** When an order is created by the `OrdersService`, it publishes an "order created" event to RabbitMQ.
    -   The `PaymentsService` and `ShippingService` subscribe to these events and process them independently, without direct knowledge of each other.
-   **Technologies:** RabbitMQ (as an ESB), Spring AMQP.

## 3. Orchestration (BPEL Workflow)

-   **Description:** A centralized process (orchestrator) coordinates and manages the execution of multiple services to achieve a complex business goal. The orchestrator has explicit control over the sequence and invocation of services.
-   **Application:**
    -   **PlaceOrder Workflow:** The BPEL engine orchestrates the `PlaceOrder` process. It receives an order request, calls the `CatalogService` (SOAP) to get book details, and then interacts with the `OrdersService` (REST) to create the order.
-   **Technologies:** Apache ODE (BPEL Engine).

## 4. Dual Security Model

-   **Description:** The system employs different security mechanisms for different service types to accommodate diverse client requirements and security standards.
-   **Application:**
    -   **WS-Security (SOAP):** Used for the `CatalogService` to provide message-level security for legacy partners.
    -   **OAuth2 (REST):** Used for the REST microservices to provide token-based authentication and authorization for modern clients.
-   **Technologies:** WS-Security, Spring Security OAuth2.

## 5. URL Path Versioning

-   **Description:** API versions are embedded directly into the URL path, allowing multiple versions of an API to coexist and enabling backward compatibility.
-   **Application:**
    -   **REST Microservices:** All REST endpoints are prefixed with `/v1/` (e.g., `/v1/orders`), ensuring that API changes can be introduced without breaking existing client integrations.
-   **Technologies:** RESTful API design principles.