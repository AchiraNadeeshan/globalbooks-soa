# GlobalBooks Inc. SOA & Microservices Project - Demo Script

This script outlines the demonstration flow for the GlobalBooks Inc. Hybrid SOA and Microservices project. The demo will showcase the coexistence and interaction of SOAP and REST services, asynchronous messaging, and BPEL orchestration.

## 1. Introduction (5 min)

-   **Project Overview:** Briefly explain the project goal: migrating a monolithic system to a hybrid SOA.
-   **Architecture Style:** Highlight the hybrid SOA approach (SOAP for legacy, REST for modern) and key components (CatalogService, OrdersService, PaymentsService, ShippingService, RabbitMQ, Apache ODE).
-   **Technology Stack:** Briefly mention core technologies (Java, Spring Boot, JAX-WS, PostgreSQL, RabbitMQ, Apache ODE).

## 2. Local Environment Setup (5 min)

-   **Prerequisites:** Briefly mention installed tools (JDK 8/18, Maven, PostgreSQL, RabbitMQ, Tomcat, Apache ODE).
-   **Configuration:** Show the `.env` file with local service ports and credentials.
-   **Service Status:** Briefly show that all services are running (e.g., via `jps` or checking logs).

## 3. CatalogService (SOAP) Demonstration (10 min)

-   **Purpose:** Explain its role for legacy partners.
-   **WSDL:** Show the `catalog.wsdl` in a browser (`http://localhost:8080/catalog-service/CatalogService?wsdl`).
-   **SOAP UI Test:**
    -   Open SOAP UI project (`testing/soapui/CatalogService-soapui-project.xml`).
    -   Show the `getBookDetails` request with WS-Security UsernameToken.
    -   Execute the request and show the successful response with book details.
    -   (Optional) Show a failed request without security or with wrong credentials.

## 4. OrdersService (REST) Demonstration (10 min)

-   **Purpose:** Explain its role for modern clients and order creation.
-   **Postman Test:**
    -   Open Postman collection (`testing/postman/OrdersService-collection.json`).
    -   Show the `Create Order` request.
    -   Explain OAuth2 security (mentioning `oauth2_token` placeholder).
    -   Execute the request and show the successful order creation response.
    -   (Optional) Show the `Order` entry in the PostgreSQL database.

## 5. Asynchronous Messaging with RabbitMQ (10 min)

-   **Purpose:** Explain how `OrdersService` communicates with `PaymentsService` and `ShippingService` asynchronously.
-   **RabbitMQ Management UI:**
    -   Open RabbitMQ Management UI (`http://localhost:15672`).
    -   Show the `orders` exchange and `orders.payments`, `orders.shipping` queues.
    -   Show messages flowing through the queues after creating an order via `OrdersService`.
-   **PaymentsService & ShippingService:** Briefly show their console logs receiving the messages.

## 6. BPEL Orchestration Demonstration (10 min)

-   **Purpose:** Explain how BPEL coordinates complex workflows involving both SOAP and REST.
-   **Apache ODE Console:**
    -   Open Apache ODE console (`http://localhost:8084/ode`).
    -   Show the deployed `PlaceOrderProcess`.
    -   Initiate a new `PlaceOrder` process (e.g., via SOAP UI or a simple client).
    -   Show the process instance in the ODE console, highlighting the invocation of `CatalogService` and `OrdersService`.
-   **BPEL Test Scenarios:** Briefly mention `success-flow.xml` and `error-handling.xml`.

## 7. Conclusion & Future Outlook (5 min)

-   **Key Achievements:** Summarize the successful implementation of a hybrid SOA, demonstrating protocol coexistence, asynchronous communication, and workflow orchestration.
-   **Challenges Overcome:** Briefly touch upon the challenges faced (e.g., JDK compatibility, security integration).
-   **Future Cloud Migration:** Discuss the readiness for cloud migration (OpenTofu, Lambda, CI/CD) as outlined in the documentation.
-   **Q&A:** Open the floor for questions.