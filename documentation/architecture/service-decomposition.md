# Service Decomposition

This document outlines the decomposition of the GlobalBooks Inc. monolithic system into a hybrid Service-Oriented Architecture (SOA) with distinct services, each responsible for a specific business capability.

## Decomposition Principles

-   **Business Capability Alignment:** Services are designed around core business functions (e.g., Catalog, Orders, Payments, Shipping).
-   **Bounded Contexts:** Each service operates within its own bounded context, owning its data and logic, minimizing shared state.
-   **Autonomy:** Services can be developed, deployed, and scaled independently.

## Service Breakdown

### 1. CatalogService (SOAP)

-   **Responsibility:** Manages book catalog information.
-   **Consumers:** Primarily legacy client systems requiring SOAP interfaces.
-   **Key Operations:** Retrieving book details by ISBN.
-   **Technology:** Java JAX-WS deployed on Apache Tomcat.

### 2. OrdersService (REST Microservice)

-   **Responsibility:** Handles the creation and management of customer orders.
-   **Consumers:** Modern client applications.
-   **Key Operations:** Creating new orders, retrieving order status.
-   **Technology:** Spring Boot application.

### 3. PaymentsService (REST Microservice)

-   **Responsibility:** Processes payment transactions.
-   **Consumers:** Triggered by order events from the `OrdersService` via RabbitMQ.
-   **Key Operations:** Authorizing and capturing payments.
-   **Technology:** Spring Boot application.

### 4. ShippingService (REST Microservice)

-   **Responsibility:** Manages the shipping process for orders.
-   **Consumers:** Triggered by order events from the `OrdersService` via RabbitMQ.
-   **Key Operations:** Initiating shipments, updating shipping status.
-   **Technology:** Spring Boot application.

## Interaction Overview

-   The `OrdersService` acts as the entry point for new orders from modern clients.
-   Upon order creation, the `OrdersService` publishes an event to RabbitMQ.
-   The `PaymentsService` and `ShippingService` consume these events asynchronously to perform their respective tasks.
-   The BPEL orchestration layer coordinates interactions between the `OrdersService` and the `CatalogService` for specific workflows (e.g., retrieving book details during order placement).