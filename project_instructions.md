### Future Cloud Migration Readiness
- [ ] Cloud migration strategy documented
- [ ] OpenTofu infrastructure templates prepared
- [ ] Lambda packaging strategy defined for REST services
- [ ] CI/CD pipeline designed for cloud deployment### Phase 13: Cloud Migration Preparation (Future Phase)
**AWS Migration Planning**:
- Document current local configuration
- Plan Lambda function packaging for REST services
- Design RDS migration strategy for databases
- Plan Amazon MQ migration for RabbitMQ
- Create OpenTofu infrastructure templates
- Design CI/CD pipeline for cloud deployment# SOA & Microservices Project - GlobalBooks Inc.

## Project Overview
Migration of GlobalBooks Inc.'s legacy monolithic order-processing system to a **Hybrid Service-Oriented Architecture (SOA)** combining SOAP services for legacy partners and REST microservices for modern scalability. The system demonstrates coexistence of different service protocols within the same SOA ecosystem.

## Architecture Style
**Hybrid SOA Architecture**:
- **SOAP Service**: CatalogService (standalone service for legacy partners)
- **REST Microservices**: OrdersService, PaymentsService, ShippingService (independent services)
- **Integration**: RabbitMQ ESB for asynchronous messaging
- **Orchestration**: BPEL engine for PlaceOrder workflow coordination

## System Integration Flow
```
Legacy Clients → SOAP CatalogService (Java WAR on Local Tomcat)
                        ↓
Modern Clients → REST OrdersService (Spring Boot Local) ←→ BPEL Orchestration (Apache ODE)
                        ↓
                Local RabbitMQ ESB Messaging
                        ↓
    PaymentsService ←→ ShippingService (Spring Boot Local Applications)
```

## Technology Stack

### Core Technologies
- **Java**: OpenJDK 18 with javac 18
- **SOAP Framework**: JAX-WS (Contract-First approach with wsimport)
- **REST Framework**: Spring Boot 3.x
- **Application Server**: Apache Tomcat (for SOAP WAR deployment)
- **Database**: PostgreSQL 17.5
- **Message Broker**: RabbitMQ ESB
- **BPEL Engine**: Apache ODE
- **Testing**: SOAP UI, Postman

### Security Stack
- **SOAP Security**: WS-Security (UsernameToken)
- **REST Security**: Spring Security OAuth2
- **Secrets Management**: .env file (development)

### Local Development Environment
- **Database**: PostgreSQL 17.5 (local installation)
- **Message Broker**: RabbitMQ (local installation or Docker)
- **Application Server**: Apache Tomcat 10.x (local installation)
- **BPEL Engine**: Apache ODE (local deployment)

### Cloud Migration Stack (Future Phase)
- **Compute**: AWS Lambda Functions (for REST services)
- **API Management**: AWS API Gateway
- **Database**: Amazon RDS PostgreSQL
- **Messaging**: Amazon MQ (RabbitMQ)
- **Monitoring**: AWS CloudWatch

### DevOps Stack
- **IaC**: OpenTofu (for cloud deployment)
- **CI/CD**: GitHub Actions
- **Versioning Strategy**: URL Path Versioning (/v1/)

## Project Structure

```
globalbooks-soa/
├── README.md
├── .env
├── .gitignore
├── governance/
│   ├── policies/
│   │   ├── versioning-policy.md
│   │   ├── sla-targets.md
│   │   └── deprecation-plan.md
│   └── uddi/
│       └── registry-entries.xml
├── services/
│   ├── catalog-service/                    # SOAP Service (Java WAR)
│   │   ├── pom.xml
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/globalbooks/catalog/
│   │   │       ├── resources/
│   │   │       │   ├── catalog.wsdl        # Contract-First WSDL
│   │   │       │   └── META-INF/
│   │   │       │       └── sun-jaxws.xml
│   │   │       └── webapp/
│   │   │           └── WEB-INF/
│   │   │               ├── web.xml
│   │   │               └── wss4j.properties
│   │   ├── generated/                      # wsimport generated classes
│   │   └── target/
│   │       └── catalog-service.war         # Deployable WAR file
│   ├── orders-service/                     # REST Microservice (Spring Boot)
│   │   ├── pom.xml
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/globalbooks/orders/
│   │   │       │       ├── OrdersApplication.java
│   │   │       │       ├── controller/     # REST Controllers
│   │   │       │       ├── model/
│   │   │       │       ├── repository/
│   │   │       │       ├── service/
│   │   │       │       └── config/         # OAuth2 Security Config
│   │   │       └── resources/
│   │   │           ├── application.yml
│   │   │           └── schema/
│   │   │               └── order-schema.json
│   │   └── lambda/
│   │       └── OrdersLambdaHandler.java    # AWS Lambda Handler
│   ├── payments-service/                   # REST Microservice (Spring Boot)
│   │   ├── pom.xml
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/globalbooks/payments/
│   │   │       │       ├── PaymentsApplication.java
│   │   │       │       ├── controller/
│   │   │       │       ├── model/
│   │   │       │       ├── service/
│   │   │       │       └── messaging/      # RabbitMQ Integration
│   │   │       └── resources/
│   │   │           └── application.yml
│   │   └── lambda/
│   │       └── PaymentsLambdaHandler.java
│   └── shipping-service/                   # REST Microservice (Spring Boot)
│       ├── pom.xml
│       ├── src/
│       │   └── main/
│       │       ├── java/
│       │       │   └── com/globalbooks/shipping/
│       │       │       ├── ShippingApplication.java
│       │       │       ├── controller/
│       │       │       ├── model/
│       │       │       ├── service/
│       │       │       └── messaging/      # RabbitMQ Integration
│       │       └── resources/
│       │           └── application.yml
│       └── lambda/
│           └── ShippingLambdaHandler.java
├── bpel/                                   # Orchestration Layer
│   ├── processes/
│   │   └── PlaceOrderProcess.bpel          # SOAP-REST Orchestration
│   ├── deploy/
│   │   └── deploy.xml                      # Apache ODE Deployment
│   └── wsdl/
│       └── PlaceOrderService.wsdl
├── integration/                            # ESB Configuration
│   ├── rabbitmq/
│   │   └── config/
│   │       ├── exchanges.json
│   │       └── queues.json
│   └── messaging/
│       ├── MessageProducer.java
│       └── MessageConfiguration.java
├── security/
│   ├── oauth2/
│   │   └── oauth2-config.yml               # REST Security
│   └── ws-security/
│       └── security-policy.xml             # SOAP Security
├── testing/
│   ├── soapui/
│   │   └── CatalogService-soapui-project.xml
│   ├── postman/
│   │   ├── OrdersService-collection.json
│   │   ├── PaymentsService-collection.json
│   │   └── ShippingService-collection.json
│   └── bpel/
│       └── test-scenarios/
│           ├── success-flow.xml
│           └── error-handling.xml
├── local-setup/
│   ├── database/
│   │   ├── postgresql-setup.md
│   │   └── init-scripts/
│   │       ├── create-databases.sql
│   │       └── sample-data.sql
│   ├── rabbitmq/
│   │   ├── rabbitmq-setup.md
│   │   └── docker-compose.rabbitmq.yml
│   ├── tomcat/
│   │   ├── tomcat-setup.md
│   │   └── server.xml
│   └── apache-ode/
│       ├── ode-setup.md
│       └── deployment-config/
│   ├── opentofu/
│   │   ├── main.tf
│   │   ├── variables.tf
│   │   ├── outputs.tf
│   │   └── modules/
│   │       ├── lambda/
│   │       ├── api-gateway/
│   │       ├── rds/
│   │       ├── rabbitmq/
│   │       └── ec2-tomcat/                 # EC2 for Tomcat deployment
│   └── scripts/
│       ├── deploy-soap.sh                  # SOAP service deployment
│       └── deploy-rest.sh                  # REST services deployment
├── .github/
│   └── workflows/
│       ├── ci-soap.yml
│       ├── ci-rest.yml
│       └── deploy.yml
├── documentation/
│   ├── architecture/
│   │   ├── hybrid-soa-design.md
│   │   ├── service-decomposition.md
│   │   └── integration-patterns.md
│   ├── api/
│   │   ├── catalog-soap-api.md
│   │   ├── orders-rest-api.md
│   │   ├── payments-rest-api.md
│   │   └── shipping-rest-api.md
│   └── deployment/
│       ├── tomcat-deployment-guide.md
│       └── aws-deployment-guide.md
├── reports/
│   └── reflective-analysis.md
└── viva/
    ├── demo-script.md
    ├── presentation-slides.pptx
    └── test-scenarios/
        ├── soap-rest-integration.md
        ├── bpel-orchestration.md
        └── messaging-flow.md
```

## Service Architecture Details

### 1. CatalogService (SOAP - Legacy Partners)
**Technology**: Java JAX-WS WAR deployed on Local Apache Tomcat
**Protocol**: SOAP with WSDL 1.1
**Security**: WS-Security UsernameToken
**Local Deployment**: WAR deployment on local Tomcat server (port 8080)
**Purpose**: Serve legacy partners who require SOAP interfaces

### 2. OrdersService (REST - Modern Clients)
**Technology**: Spring Boot standalone application
**Protocol**: REST with JSON
**Security**: OAuth2 with Spring Security
**Local Deployment**: Spring Boot embedded server (port 8081)
**Versioning**: URL Path (/v1/orders)
**Purpose**: Handle order creation and management for modern clients

### 3. PaymentsService (REST - Flexible Choice)
**Technology**: Spring Boot standalone application
**Protocol**: REST with JSON
**Integration**: RabbitMQ message consumer
**Local Deployment**: Spring Boot embedded server (port 8082)
**Purpose**: Process payments asynchronously via message queues

### 4. ShippingService (REST - Flexible Choice)
**Technology**: Spring Boot standalone application
**Protocol**: REST with JSON  
**Integration**: RabbitMQ message consumer
**Local Deployment**: Spring Boot embedded server (port 8083)
**Purpose**: Handle shipping coordination via message queues

## System Integration Architecture

### BPEL Orchestration Layer
- **PlaceOrder Workflow**: Coordinates between SOAP CatalogService and REST OrdersService
- **Mixed Protocol Handling**: BPEL process handles both SOAP and REST service invocations
- **Engine**: Apache ODE deployment

### RabbitMQ ESB Integration
- **Asynchronous Messaging**: PaymentsService and ShippingService communicate via message queues
- **Event-Driven Architecture**: Order events trigger payment and shipping processes
- **Error Handling**: Dead letter queues for failed message processing

### Security Implementation
- **Dual Security Model**: WS-Security for SOAP, OAuth2 for REST
- **Token Management**: Separate authentication mechanisms for different service types
- **Cross-Protocol Security**: BPEL orchestration handles security context translation

## Local Development Setup Instructions

### Prerequisites
- Java OpenJDK 18 with javac 18 installed
- Apache Tomcat 10.x installed locally
- PostgreSQL 17.5 installed locally
- RabbitMQ server installed locally (or Docker)
- Apache ODE BPEL engine
- Maven 3.8+ for project builds
- SOAP UI for testing
- Postman for REST API testing

### Local Environment Configuration (.env file)
```
# Local Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME_CATALOG=globalbooks_catalog
DB_NAME_ORDERS=globalbooks_orders
DB_NAME_PAYMENTS=globalbooks_payments
DB_NAME_SHIPPING=globalbooks_shipping
DB_USERNAME=admin
DB_PASSWORD=secure_password

# Local RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
RABBITMQ_MANAGEMENT_PORT=15672

# Local Service Ports
TOMCAT_PORT=8080
ORDERS_SERVICE_PORT=8081
PAYMENTS_SERVICE_PORT=8082
SHIPPING_SERVICE_PORT=8083
ODE_PORT=8084

# OAuth2 Configuration
OAUTH2_CLIENT_ID=globalbooks-client
OAUTH2_CLIENT_SECRET=your-secret-here
OAUTH2_TOKEN_URI=http://localhost:9000/oauth2/token

# Service URLs (Local)
CATALOG_SERVICE_URL=http://localhost:8080/catalog-service
ORDERS_SERVICE_URL=http://localhost:8081
PAYMENTS_SERVICE_URL=http://localhost:8082
SHIPPING_SERVICE_URL=http://localhost:8083
BPEL_SERVICE_URL=http://localhost:8084/ode
```

## Implementation Instructions for AI Agent

### Phase 1: Local Environment Setup
**Local Infrastructure Setup**:
- Install and configure PostgreSQL 17.5 locally
- Create separate databases for each service
- Install RabbitMQ server locally with management plugin
- Configure Apache Tomcat 10.x for SOAP service deployment
- Set up Apache ODE BPEL engine locally
- Create local startup and shutdown scripts

### Phase 2: SOA Design Principles Implementation
**Focus**: Implement the four chosen SOA principles
- **Loose Coupling**: Services communicate through well-defined interfaces only
- **Service Autonomy**: Each service manages its own data and business logic independently  
- **Service Reusability**: Services designed for reuse across different business processes
- **Service Composability**: Services can be combined in BPEL workflows

### Phase 3: SOAP Service Implementation (Contract-First)
**CatalogService Requirements**:
- Create WSDL 1.1 contract first
- Generate Java classes using wsimport
- Implement JAX-WS service with proper annotations
- Configure sun-jaxws.xml and web.xml
- Package as deployable WAR file for local Tomcat
- Implement WS-Security UsernameToken authentication
- Deploy to local Tomcat server on port 8080

### Phase 4: REST Microservices Implementation
**OrdersService, PaymentsService, ShippingService Requirements**:
- Spring Boot applications with embedded servers
- Different ports for each service (8081, 8082, 8083)
- JSON request/response handling
- OAuth2 security integration
- Local PostgreSQL database connections
- Local development profiles in application.yml
- Health check endpoints for monitoring

### Phase 5: BPEL Orchestration
**PlaceOrder Process Requirements**:
- Deploy Apache ODE locally on port 8084
- Receive order request
- Invoke local SOAP CatalogService for book details
- Invoke local REST OrdersService to create order
- Handle mixed protocol communication
- Local BPEL process deployment and testing

### Phase 6: RabbitMQ ESB Integration
**Local Messaging Requirements**:
- Configure local RabbitMQ server with exchanges and queues
- Implement message producers in OrdersService
- Implement message consumers in PaymentsService and ShippingService
- Local queue monitoring via RabbitMQ Management UI (port 15672)
- Error handling with dead letter queues
- QoS configuration for reliable messaging

### Phase 7: UDDI Registry Implementation
**Service Discovery Requirements**:
- Create UDDI registry entries for service metadata
- Business entity and service definitions
- Access point configurations
- Technical model specifications

### Phase 8: Security Configuration
**Dual Security Model**:
- WS-Security policy configuration for SOAP services
- OAuth2 resource server setup for REST services (can use local OAuth2 server)
- Security context management in BPEL processes
- Token validation and authentication flows
- Local security testing with different authentication methods

### Phase 9: Governance Policies
**URL Path Versioning Strategy**:
- Implement /v1/ prefix for all REST endpoints
- Version compatibility matrix
- SLA targets: 99.5% uptime, sub-200ms response time
- Deprecation schedule and sunset process

### Phase 10: Local Testing Strategy
**Multi-Protocol Testing**:
- SOAP UI testing for CatalogService SOAP operations (localhost:8080)
- Postman collections for REST microservices (ports 8081, 8082, 8083)
- BPEL process testing scenarios via ODE console (localhost:8084)
- Integration testing across SOAP-REST boundaries
- Local security authentication testing for both protocols
- RabbitMQ message flow testing via Management UI

### Phase 11: Local Monitoring and Logging
**Development Monitoring**:
- Spring Boot Actuator endpoints for health checks
- Tomcat access logs and application logs
- RabbitMQ queue monitoring via Management UI
- PostgreSQL connection monitoring
- Application-specific logging with correlation IDs
- Simple metrics collection for performance analysis

### Phase 12: Documentation and Analysis
**Comprehensive Documentation**:
- Hybrid SOA architecture decisions
- SOAP vs REST protocol trade-offs
- Integration pattern analysis
- Security model comparisons
- Performance benchmarking results

## Validation Checklist

### Local Development Setup
- [ ] PostgreSQL 17.5 installed and configured locally
- [ ] RabbitMQ server running locally with management plugin
- [ ] Apache Tomcat 10.x configured for SOAP service deployment
- [ ] Apache ODE BPEL engine deployed and running locally
- [ ] All services running on designated local ports

### Service Implementation
- [ ] CatalogService deployed as Java WAR on local Tomcat (port 8080)
- [ ] OrdersService implemented as Spring Boot application (port 8081)
- [ ] PaymentsService implemented as Spring Boot application (port 8082)
- [ ] ShippingService implemented as Spring Boot application (port 8083)

### Integration and Orchestration
- [ ] BPEL PlaceOrder process deployed on local Apache ODE (port 8084)
- [ ] Local RabbitMQ handling asynchronous messaging between services
- [ ] UDDI registry entries created for service discovery

### Security Implementation
- [ ] WS-Security UsernameToken implemented for SOAP CatalogService
- [ ] OAuth2 authentication implemented for all REST services
- [ ] Cross-protocol security handled in BPEL orchestration

### Testing and Quality
- [ ] SOAP UI project testing CatalogService operations on localhost:8080
- [ ] Postman collections testing all REST endpoints on respective ports
- [ ] BPEL process execution validated with test scenarios via ODE console
- [ ] Integration testing across SOAP-REST service boundaries locally
- [ ] Local RabbitMQ message flow testing via Management UI

### Local Development Operations
- [ ] Local PostgreSQL databases operational for each service
- [ ] Local RabbitMQ message queues processing correctly
- [ ] All services accessible via localhost with proper port configuration
- [ ] Local monitoring via Spring Boot Actuator and RabbitMQ Management UI

### Governance and Documentation
- [ ] URL path versioning implemented (/v1/)
- [ ] SLA targets monitored (99.5% uptime, sub-200ms response)
- [ ] Governance policies documented and implemented
- [ ] Comprehensive technical documentation completed
- [ ] Reflective analysis covering hybrid architecture trade-offs

## Key Success Factors for AI Agent

1. **Local Development First**: Set up complete local environment before considering cloud migration
2. **Port Management**: Ensure each service runs on designated ports without conflicts
3. **Protocol Coexistence**: Demonstrate SOAP and REST working locally within the same SOA ecosystem
4. **Contract-First SOAP**: Always start with WSDL design before Java implementation
5. **Spring Boot Configuration**: Use local profiles and embedded servers for development
6. **Local Database Setup**: Create separate PostgreSQL databases for each service
7. **RabbitMQ Integration**: Ensure local message broker handles asynchronous communication
8. **BPEL Local Deployment**: Apache ODE must orchestrate local SOAP and REST services
9. **Local Testing**: Validate both individual services and cross-protocol integration locally
10. **Migration Preparation**: Document local setup for future cloud migration planning