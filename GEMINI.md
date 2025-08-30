# GlobalBooks Inc. SOA & Microservices Project: Implementation Plan

This document outlines the comprehensive plan for migrating GlobalBooks Inc.'s legacy monolithic order-processing system to a Hybrid Service-Oriented Architecture (SOA).

## 1. Project Overview

The project will create a hybrid system combining SOAP and REST services. This approach supports legacy partners while enabling modern, scalable microservices for new applications. The system will be built and tested locally, with a future-ready design for cloud migration.

## 2. System Architecture

The architecture is a Hybrid SOA model:

-   **SOAP Service (Legacy):** `CatalogService` for existing partners.
-   **REST Microservices (Modern):** `OrdersService`, `PaymentsService`, and `ShippingService` for scalability.
-   **Integration:** RabbitMQ for asynchronous messaging between services.
-   **Orchestration:** A BPEL engine will manage the `PlaceOrder` workflow, coordinating between the SOAP and REST services.

### System Integration Flow

```
Legacy Clients -> SOAP CatalogService (Java WAR on Local Tomcat)
                        |
Modern Clients -> REST OrdersService (Spring Boot Local) <-> BPEL Orchestration (Apache ODE)
                        |
                Local RabbitMQ ESB Messaging
                        |
    PaymentsService <-> ShippingService (Spring Boot Local Applications)
```

## 3. Technology Stack

-   **Java:**
    -   **JDK 8:** For the SOAP `catalog-service` (due to the removal of `wsimport` in later JDKs).
    -   **OpenJDK 18:** For all REST microservices.
-   **SOAP:** JAX-WS (Contract-First)
-   **REST:** Spring Boot 3.x
-   **Application Server:** Apache Tomcat 10.x
-   **Database:** PostgreSQL 17.5
-   **Message Broker:** RabbitMQ
-   **BPEL Engine:** Apache ODE
-   **Security:** WS-Security (SOAP), OAuth2 (REST)
-   **Testing:** SOAP UI, Postman

## 4. Implementation Plan

The project will be implemented in the following phases:

### Phase 1: Local Environment Setup

1.  **Install Tools:**
    *   Install JDK 8, OpenJDK 18, Maven 3.8+, and set `JAVA_HOME` to OpenJDK 18.
    *   **Note:** Please provide the path to your JDK 8 installation.
    *   Install Apache Tomcat 10.x.
    *   Install PostgreSQL 17.5.
    *   Install RabbitMQ (or use Docker).
    *   Install Apache ODE.
    *   Install SOAP UI and Postman.
2.  **Configure Environment:**
    *   Create a `.env` file with all necessary credentials and connection details for local services.
    *   Write setup scripts (`local-setup/scripts/`) for starting and stopping services.
3.  **Database Setup:**
    *   Create separate PostgreSQL databases: `globalbooks_catalog`, `globalbooks_orders`, `globalbooks_payments`, `globalbooks_shipping`.
    *   Run initial schema creation scripts (`local-setup/database/init-scripts/`).

### Phase 2: Governance and Project Structure

1.  **Create Project Structure:**
    *   Set up the complete directory structure as defined in `project_instructions.md`.
2.  **Define Governance Policies:**
    *   Create markdown files for:
        *   `governance/policies/versioning-policy.md`
        *   `governance/policies/sla-targets.md`
        *   `governance/policies/deprecation-plan.md`
3.  **UDDI Registry:**
    *   Create `governance/uddi/registry-entries.xml` to define service metadata.

### Phase 3: SOAP Service Implementation (`CatalogService`)

1.  **WSDL Contract:**
    *   Create `services/catalog-service/src/main/resources/catalog.wsdl`.
2.  **Generate Java Classes:**
    *   Use `wsimport` to generate Java classes from the WSDL into the `generated/` directory.
3.  **Implement Service Logic:**
    *   Implement the JAX-WS service endpoint.
    *   Configure `sun-jaxws.xml` and `web.xml`.
4.  **Security:**
    *   Implement WS-Security with UsernameToken. Configure `wss4j.properties`.
5.  **Build and Deploy:**
    *   Configure `pom.xml` to build a WAR file.
    *   Deploy the `catalog-service.war` to the local Tomcat server.

### Phase 4: REST Microservices Implementation

1.  **`OrdersService`:**
    *   Create a Spring Boot project.
    *   Implement REST controllers, services, repositories, and models.
    *   Configure `application.yml` for port 8081 and database connection.
    *   Set up OAuth2 security.
2.  **`PaymentsService`:**
    *   Create a Spring Boot project.
    *   Implement REST controllers and services.
    *   Configure `application.yml` for port 8082 and database connection.
    *   Integrate RabbitMQ consumer for payment processing.
3.  **`ShippingService`:**
    *   Create a Spring Boot project.
    *   Implement REST controllers and services.
    *   Configure `application.yml` for port 8083 and database connection.
    *   Integrate RabbitMQ consumer for shipping updates.

### Phase 5: BPEL Orchestration (`PlaceOrderProcess`)

1.  **WSDL for BPEL Process:**
    *   Create `bpel/wsdl/PlaceOrderService.wsdl`.
2.  **BPEL Process:**
    *   Create the `PlaceOrderProcess.bpel` file.
    *   Orchestrate calls to the SOAP `CatalogService` and the REST `OrdersService`.
3.  **Deployment:**
    *   Create `bpel/deploy/deploy.xml`.
    *   Deploy the process to the local Apache ODE engine.

### Phase 6: RabbitMQ ESB Integration

1.  **Configuration:**
    *   Define exchanges and queues in `integration/rabbitmq/config/`.
2.  **Message Producers/Consumers:**
    *   Implement message producers in `OrdersService` to publish events.
    *   Implement message consumers in `PaymentsService` and `ShippingService`.
3.  **Error Handling:**
    *   Configure dead-letter queues for failed messages.

### Phase 7: Security Configuration

1.  **SOAP Security:**
    *   Define the security policy in `security/ws-security/security-policy.xml`.
2.  **REST Security:**
    *   Configure the OAuth2 server and define settings in `security/oauth2/oauth2-config.yml`.

### Phase 8: Testing

1.  **SOAP Testing:**
    *   Create a SOAP UI project (`testing/soapui/`) to test `CatalogService`.
2.  **REST Testing:**
    *   Create Postman collections (`testing/postman/`) for each REST microservice.
3.  **BPEL Testing:**
    *   Create test scenarios for the `PlaceOrderProcess` in `testing/bpel/test-scenarios/`.
4.  **Integration Testing:**
    *   Develop end-to-end tests covering the entire workflow.

### Phase 9: Documentation

1.  **Architecture Documentation:**
    *   Write detailed markdown files in `documentation/architecture/`.
2.  **API Documentation:**
    *   Create API documentation for all services in `documentation/api/`.
3.  **Deployment Guides:**
    *   Write deployment guides for Tomcat and AWS in `documentation/deployment/`.

### Phase 10: Cloud Migration Preparation

1.  **Infrastructure as Code:**
    *   Create OpenTofu templates (`local-setup/opentofu/`) for AWS resources.
2.  **Lambda Handlers:**
    *   Implement Lambda handler classes in each microservice project.
3.  **CI/CD Pipelines:**
    *   Define GitHub Actions workflows (` .github/workflows/`) for CI and deployment.

## 5. Next Steps

This plan provides a comprehensive roadmap. I will now proceed with the implementation, starting with Phase 1.
