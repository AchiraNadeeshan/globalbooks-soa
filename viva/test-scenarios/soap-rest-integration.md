# SOAP-REST Integration Scenarios

This document details the integration scenarios between SOAP and REST services within the GlobalBooks Inc. Hybrid SOA.

## 1. BPEL Orchestration of SOAP and REST

**Scenario:** Placing an order that requires book details from the `CatalogService` (SOAP) and then creating the order via the `OrdersService` (REST).

-   **Flow:**
    1.  Client initiates `PlaceOrderProcess` (BPEL).
    2.  BPEL process invokes `CatalogService` (SOAP) to retrieve book details using the provided ISBN.
    3.  `CatalogService` returns book details.
    4.  BPEL process then invokes `OrdersService` (REST) to create the order, passing the retrieved book details and order quantity.
    5.  `OrdersService` creates the order and publishes an event to RabbitMQ.
    6.  BPEL process completes.

-   **Key Components:** Apache ODE, `CatalogService` (JAX-WS), `OrdersService` (Spring Boot REST).
-   **Integration Challenge:** Handling different message formats (XML for SOAP, JSON for REST) and transport protocols within a single workflow. BPEL handles the transformation and invocation.

## 2. Asynchronous Event-Driven Integration (REST Microservices)

**Scenario:** An order is created, triggering payment processing and shipping coordination asynchronously.

-   **Flow:**
    1.  Client calls `OrdersService` (REST) to create an order.
    2.  `OrdersService` persists the order and publishes an "order.created" event to the `orders` topic exchange in RabbitMQ.
    3.  `PaymentsService` (REST) consumes the "order.created" event from the `orders.payments` queue.
    4.  `PaymentsService` processes the payment.
    5.  `ShippingService` (REST) consumes the "order.created" event from the `orders.shipping` queue.
    6.  `ShippingService` initiates shipping coordination.

-   **Key Components:** `OrdersService`, `PaymentsService`, `ShippingService` (all Spring Boot REST), RabbitMQ.
-   **Integration Benefit:** Decoupling of services, allowing independent scaling and failure isolation. The `OrdersService` does not need to wait for payment or shipping to complete.

## 3. Security Context Propagation

**Scenario:** Ensuring security context is maintained or translated across different service protocols.

-   **Flow:**
    -   For SOAP calls from BPEL to `CatalogService`, the BPEL engine is responsible for including the necessary WS-Security headers (e.g., UsernameToken).
    -   For REST calls from BPEL to `OrdersService`, the BPEL engine would need to obtain and include an OAuth2 Bearer Token in the `Authorization` header.

-   **Key Components:** WS-Security, OAuth2, Apache ODE.
-   **Integration Challenge:** Translating authentication and authorization mechanisms between different security standards.
