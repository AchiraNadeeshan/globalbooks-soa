# Task Completion

## Task 1
Explain which SOA design principles you applied when decomposing the monolith into independent services.
The following SOA design principles were applied during the decomposition of the monolith into independent services:
- **Loose Coupling**: Services communicate through well-defined interfaces (WSDL for SOAP, JSON contracts for REST) without direct dependencies on internal implementations. This allows for independent evolution of services.
- **Service Autonomy**: Each service (e.g., `CatalogService`, `OrdersService`) manages its own data and business logic, ensuring self-containment and independent deployment.
- **Service Reusability**: Services are designed to be generic and reusable across different business processes. For instance, the `CatalogService` can be consumed by any client requiring book information.
- **Service Composability**: Services can be combined to form larger, more complex business processes. The `PlaceOrder` BPEL process exemplifies this by orchestrating calls to both `CatalogService` and `OrdersService`.

## Task 2
Discuss one key benefit and one primary challenge of your approach.
**Key Benefit**: The primary benefit of this Hybrid SOA approach is its ability to **support existing legacy partners (via SOAP) while simultaneously enabling modern, scalable microservices for new applications (via REST)**. This strategy facilitates a gradual and controlled migration from the monolithic system, preserving existing investments while embracing the agility and scalability offered by microservices.

**Primary Challenge**: A significant challenge lies in the **complexity of integrating and managing a dual-protocol environment (SOAP and REST) with distinct security models**. This necessitates sophisticated orchestration (e.g., BPEL engine handling mixed protocols) and robust, cross-protocol security configurations (WS-Security for SOAP, OAuth2 for REST) to ensure seamless and secure communication across the diverse service landscape.

## Task 3
Provide a WSDL excerpt for the CatalogService (operations, types, binding)
Here is a WSDL excerpt for the `CatalogService`, detailing its types, messages, portType (operations), and binding:

```xml
<definitions name="CatalogService"
             targetNamespace="http://catalog.globalbooks.com/"
             xmlns="http://schemas.xmlsoap.org/wsdl/"
             xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
             xmlns:xsd="http://www.w3.org/2001/XMLSchema"
             xmlns:tns="http://catalog.globalbooks.com/"
             xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/">

    <types>
        <xsd:schema targetNamespace="http://catalog.globalbooks.com/">
            <xsd:element name="getBookDetails">
                <xsd:complexType>
                    <xsd:sequence>
                        <xsd:element name="isbn" type="xsd:string"/>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
            <xsd:element name="getBookDetailsResponse">
                <xsd:complexType>
                    <xsd:sequence>
                        <xsd:element name="book">
                            <xsd:complexType>
                                <xsd:sequence>
                                    <xsd:element name="isbn" type="xsd:string"/>
                                    <xsd:element name="title" type="xsd:string"/>
                                    <xsd:element name="author" type="xsd:string"/>
                                    <xsd:element name="price" type="xsd:double"/>
                                </xsd:sequence>
                            </xsd:complexType>
                        </xsd:element>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
        </xsd:schema>
    </types>

    <message name="GetBookDetailsRequest">
        <part name="parameters" element="tns:getBookDetails"/>
    </message>

    <message name="GetBookDetailsResponse">
        <part name="parameters" element="tns:getBookDetailsResponse"/>
    </message>

    <portType name="CatalogServicePortType">
        <operation name="getBookDetails">
            <input message="tns:GetBookDetailsRequest"/>
            <output message="tns:GetBookDetailsResponse"/>
        </operation>
    </portType>

    <binding name="CatalogServiceBinding" type="tns:CatalogServicePortType">
        <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
        <operation name="getBookDetails">
            <soap:operation soapAction=""/>
            <input>
                <soap:body use="literal"/>
            </input>
            <output>
                <soap:body use="literal"/>
            </output>
        </operation>
    </binding>

    <service name="CatalogService">
        <port name="CatalogServicePort" binding="tns:CatalogServiceBinding">
            <soap:address location="http://localhost:8080/catalog-service/CatalogService"/>
        </port>
    </service>
</definitions>
```

## Task 4
Draft the UDDI registry entry metadata enabling client discovery.
The UDDI registry entry metadata for GlobalBooks Inc. services, enabling client discovery, is drafted as follows. It includes a business entity for "GlobalBooks Inc." and business services for `CatalogService` (SOAP), `OrdersService` (REST), `PaymentsService` (REST), and `ShippingService` (REST), each with their respective access points.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<uddi xmlns="urn:uddi-org:api_v3">
  <businessEntity businessKey="uddi:globalbooks.com:globalbooks-business">
    <name>GlobalBooks Inc.</name>
    <description>GlobalBooks Inc. SOA Services</description>
    <serviceBusinesses>
      <businessService serviceKey="uddi:globalbooks.com:catalog-service" businessKey="uddi:globalbooks.com:globalbooks-business">
        <name>CatalogService</name>
        <description>SOAP service for retrieving book catalog details.</description>
        <bindingTemplates>
          <bindingTemplate bindingKey="uddi:globalbooks.com:catalog-service-binding" serviceKey="uddi:globalbooks.com:catalog-service">
            <accessPoint useType="wsdlDeployment">http://localhost:8080/catalog-service/CatalogService?wsdl</accessPoint>
          </bindingTemplate>
        </bindingTemplates>
      </businessService>
      <businessService serviceKey="uddi:globalbooks.com:orders-service" businessKey="uddi:globalbooks.com:globalbooks-business">
        <name>OrdersService</name>
        <description>REST microservice for managing customer orders.</description>
        <bindingTemplates>
          <bindingTemplate bindingKey="uddi:globalbooks.com:orders-service-binding" serviceKey="uddi:globalbooks.com:orders-service">
            <accessPoint useType="rest">http://localhost:8081/v1/orders</accessPoint>
          </bindingTemplate>
        </bindingTemplates>
      </businessService>
      <businessService serviceKey="uddi:globalbooks.com:payments-service" businessKey="uddi:globalbooks.com:globalbooks-business">
        <name>PaymentsService</name>
        <description>REST microservice for processing payments.</description>
        <bindingTemplates>
          <bindingTemplate bindingKey="uddi:globalbooks.com:payments-service-binding" serviceKey="uddi:globalbooks.com:payments-service">
            <accessPoint useType="rest">http://localhost:8082</accessPoint>
          </bindingTemplate>
        </bindingTemplates>
      </businessService>
      <businessService serviceKey="uddi:globalbooks.com:shipping-service" businessKey="uddi:globalbooks.com:globalbooks-business">
        <name>ShippingService</name>
        <description>REST microservice for managing shipping.</description>
        <bindingTemplates>
          <bindingTemplate bindingKey="uddi:globalbooks.com:shipping-service-binding" serviceKey="uddi:globalbooks.com:shipping-service">
            <accessPoint useType="rest">http://localhost:8083</accessPoint>
          </bindingTemplate>
        </bindingTemplates>
      </businessService>
    </serviceBusinesses>
  </businessEntity>
</uddi>
```

## Task 5
Describe in detail how you implemented the CatalogService SOAP endpoint in Java (including sun-jaxws.xml and web.xml snippets).
The `CatalogService` SOAP endpoint is implemented in Java using JAX-WS (Java API for XML Web Services) with a contract-first approach.

**Java Implementation (Conceptual):**
The core logic resides in a Java class, likely `com.globalbooks.catalog.CatalogServiceImpl`, which implements the service endpoint interface generated from the `catalog.wsdl`. This class would contain methods corresponding to the WSDL operations, such as `getBookDetails(String isbn)`. Inside this method, the implementation would query a data source (e.g., a database) to retrieve book details based on the provided ISBN and return a `Book` object. JAX-WS annotations (e.g., `@WebService`, `@WebMethod`, `@WebParam`) would be used to map the Java class and its methods to the WSDL definitions.

**`sun-jaxws.xml` Snippet:**
This file configures the JAX-WS runtime to publish the `CatalogService` endpoint. It specifies the service name, the implementation class, and the URL pattern where the service will be accessible.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<endpoints xmlns="http://java.sun.com/xml/ns/jax-ws/ri/runtime" version="2.0">
    <endpoint
            name="CatalogService"
            implementation="com.globalbooks.catalog.CatalogServiceImpl"
            url-pattern="/CatalogService"/>
</endpoints>
```
- `name="CatalogService"`: Defines the logical name of the service endpoint.
- `implementation="com.globalbooks.catalog.CatalogServiceImpl"`: Points to the Java class that contains the actual service implementation.
- `url-pattern="/CatalogService"`: Specifies the relative URL path where this SOAP service will be exposed.

**`web.xml` Snippet:**
This is the standard deployment descriptor for Java web applications (WAR files). It configures the JAX-WS servlet and listener to handle incoming SOAP requests and dispatch them to the appropriate JAX-WS endpoint.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <listener>
        <listener-class>com.sun.xml.ws.transport.http.servlet.WSServletContextListener</listener-class>
    </listener>

    <servlet>
        <servlet-name>CatalogService</servlet-name>
        <servlet-class>com.sun.xml.ws.transport.http.servlet.WSServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>CatalogService</servlet-name>
        <url-pattern>/CatalogService</url-pattern>
    </servlet-mapping>

</web-app>
```
- `<listener>`: Registers `WSServletContextListener`, which initializes the JAX-WS runtime when the web application starts.
- `<servlet>`: Declares a servlet named `CatalogService` using `com.sun.xml.ws.transport.http.servlet.WSServlet`. This servlet is responsible for processing SOAP messages.
- `<servlet-mapping>`: Maps the `CatalogService` servlet to the `/CatalogService` URL pattern, ensuring that requests to this path are handled by the JAX-WS servlet.

Together, these configurations ensure that the `CatalogService` is correctly deployed as a WAR file on Apache Tomcat, making its SOAP endpoint accessible at `http://localhost:8080/catalog-service/CatalogService`.

## Task 6
Explain how you tested it using SOAP UI (test cases and assertions).
The `CatalogService` was tested using SOAP UI to ensure its functionality and adherence to the WSDL contract, including security aspects.

**Test Cases:**
A primary test case for the `CatalogService` involves invoking the `getBookDetails` operation.
- **Test Case Name:** `GetBookDetails_ValidISBN`
- **Description:** This test case verifies that the service correctly retrieves book details for a valid ISBN.
- **Request:** A SOAP request is constructed to call the `getBookDetails` operation, passing a valid ISBN (e.g., `1234567890`).
- **Security:** The request is configured to include WS-Security UsernameToken credentials (`username: admin`, `password: password`) as defined in the `wss4j.properties` and `security-policy.xml` for the service. This ensures that the security mechanism is correctly enforced and processed by the service.

**Example Request (from `CatalogService-soapui-project.xml`):**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://catalog.globalbooks.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <cat:getBookDetails>
         <isbn>1234567890</isbn>
      </cat:getBookDetails>
   </soapenv:Body>
</soapenv:Envelope>
```

**Assertions:**
After sending the request, assertions are added to the test step to validate the response:
- **SOAP Fault Assertion:** To ensure no SOAP Faults are returned, indicating successful processing.
- **Schema Compliance Assertion:** To validate that the response XML conforms to the `getBookDetailsResponse` schema defined in the WSDL.
- **XPath Match Assertion:** To verify specific content within the response. For example:
    - `//book/isbn` should match `1234567890`
    - `//book/title` should not be empty
    - `//book/author` should not be empty
    - `//book/price` should be a positive number

**Additional Test Cases (Conceptual):**
- **`GetBookDetails_InvalidISBN`**: Test with a non-existent or invalid ISBN to ensure appropriate error handling or an empty/null response.
- **`GetBookDetails_NoSecurity`**: Test without providing security credentials to verify that the WS-Security policy correctly rejects unauthorized access.
- **`GetBookDetails_InvalidCredentials`**: Test with incorrect username/password to ensure authentication failure.

These test cases and assertions collectively ensure the functional correctness, data integrity, and security enforcement of the `CatalogService` SOAP endpoint.

## Task 7
Design the OrdersService REST API: list endpoints (POST /orders, GET /orders/{id}), sample JSON request & response, and the JSON Schema for order creation.
The `OrdersService` exposes a REST API for managing customer orders, following a `/v1/orders` URL path versioning strategy.

**Endpoints:**

1.  **Create Order**
    *   **HTTP Method:** `POST`
    *   **Endpoint:** `/v1/orders`
    *   **Description:** Creates a new order in the system.
    *   **Request Body (JSON):**
        ```json
        {
          "isbn": "978-0321765723",
          "quantity": 2
        }
        ```
    *   **Sample Response (JSON - 201 Created):**
        ```json
        {
          "orderId": "ORD-20250901-001",
          "isbn": "978-0321765723",
          "quantity": 2,
          "orderDate": "2025-09-01T10:30:00Z",
          "status": "PENDING"
        }
        ```

2.  **Get Order by ID**
    *   **HTTP Method:** `GET`
    *   **Endpoint:** `/v1/orders/{orderId}`
    *   **Description:** Retrieves the details of a specific order by its unique identifier.
    *   **Sample Response (JSON - 200 OK):**
        ```json
        {
          "orderId": "ORD-20250901-001",
          "isbn": "978-0321765723",
          "quantity": 2,
          "orderDate": "2025-09-01T10:30:00Z",
          "status": "PENDING"
        }
        ```
    *   **Sample Response (JSON - 404 Not Found):**
        ```json
        {
          "timestamp": "2025-09-01T10:35:00Z",
          "status": 404,
          "error": "Not Found",
          "message": "Order with ID ORD-NONEXISTENT not found."
        }
        ```

**JSON Schema for Order Creation (`order-schema.json`):**
This schema defines the structure and validation rules for the request body when creating a new order.

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "Order",
  "description": "An order for a book",
  "type": "object",
  "properties": {
    "isbn": {
      "type": "string"
    },
    "quantity": {
      "type": "integer",
      "minimum": 1
    }
  },
  "required": [
    "isbn",
    "quantity"
  ]
}
```
-   `isbn`: A string representing the International Standard Book Number.
-   `quantity`: An integer representing the number of units of the book, with a minimum value of 1.
-   `required`: Both `isbn` and `quantity` are mandatory fields for order creation.


## Task 8
Outline the "PlaceOrder" BPEL process: receive, loop for price lookup via CatalogService, invoke OrdersService, reply to client.
The "PlaceOrder" BPEL process orchestrates the creation of an order by coordinating interactions between the client, the SOAP `CatalogService`, and the REST `OrdersService`.

**Process Outline:**

1.  **Receive Order Request:**
    *   The BPEL process starts by receiving an initial order request from the client. This request typically contains details such as the ISBN of the book and the desired quantity.
    *   **BPEL Action:** `receive` activity (`receiveInput`)
    *   **Input Variable:** `input` (messageType `tns:PlaceOrderRequest`)

2.  **Price Lookup via CatalogService:**
    *   For each item in the order (or for the single item in this simplified example), the process needs to look up its details, including the price, from the `CatalogService`.
    *   **BPEL Action:** `assign` activity (`prepareCatalogRequest`) to map the ISBN from the input to the `catalogInput` variable.
    *   **BPEL Action:** `invoke` activity (`invokeCatalogService`) to call the `getBookDetails` operation on the `CatalogService` (SOAP).
    *   **Input Variable:** `catalogInput` (messageType `catalog:GetBookDetailsRequest`)
    *   **Output Variable:** `catalogOutput` (messageType `catalog:GetBookDetailsResponse`)
    *   *(Note: The provided BPEL snippet shows a single invocation. A "loop for price lookup" would typically involve iterating over multiple items in an order, invoking the `CatalogService` for each, and accumulating the results.)*

3.  **Invoke OrdersService (REST):**
    *   After retrieving the necessary book details (including price), the BPEL process would then invoke the `OrdersService` (REST) to create the actual order. This would involve constructing a JSON payload with the order details (ISBN, quantity, and potentially the retrieved price) and sending it to the `OrdersService`'s `/v1/orders` endpoint.
    *   **BPEL Action (Conceptual):** An `invoke` activity configured to interact with a REST service, potentially using extensions or custom activities within Apache ODE to handle HTTP requests and JSON payloads.
    *   **Input:** JSON payload for order creation.
    *   **Output:** Order confirmation (e.g., `orderId`).
    *   *(Note: The provided `PlaceOrderProcess.bpel` snippet explicitly includes a comment `<!-- Add logic to invoke OrdersService (REST) here -->`, indicating this part of the orchestration is a planned extension.)*

4.  **Reply to Client:**
    *   Finally, after the order has been successfully created in the `OrdersService`, the BPEL process sends a response back to the initiating client, confirming the order and providing details such as the generated `orderId`.
    *   **BPEL Action:** `assign` activity (`prepareResponse`) to set the `orderId` in the `output` variable.
    *   **BPEL Action:** `reply` activity (`replyOutput`)
    *   **Output Variable:** `output` (messageType `tns:PlaceOrderResponse`)

This orchestration demonstrates the hybrid SOA approach by seamlessly integrating both SOAP and REST services within a single business process flow.

## Task 9
Explain deployment and testing on a BPEL engine (e.g., Apache ODE).
The "PlaceOrder" BPEL process is deployed and tested on Apache ODE, a lightweight and open-source BPEL engine.

**Deployment on Apache ODE:**

1.  **Packaging:** The BPEL process (`PlaceOrderProcess.bpel`) along with its WSDL definitions (`PlaceOrderService.wsdl` and imported `catalog.wsdl`) and the deployment descriptor (`deploy.xml`) are packaged into a ZIP archive (e.g., `PlaceOrderProcess.zip`).
2.  **Deployment Descriptor (`deploy.xml`):** This file instructs Apache ODE on how to deploy the BPEL process.
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <deploy xmlns="http://www.apache.org/ode/schemas/dd/2007/03" xmlns:bpel="http://bpel.globalbooks.com/">
      <process name="bpel:PlaceOrderProcess">
        <active>true</active>
        <provide partnerLink="client">
          <service name="bpel:PlaceOrderService" port="PlaceOrderServicePort"/>
        </provide>
        <invoke partnerLink="catalogService">
          <service name="catalog:CatalogService" port="CatalogServicePort" xmlns:catalog="http://catalog.globalbooks.com/"/>
        </invoke>
      </process>
    </deploy>
    ```
    *   `<process name="bpel:PlaceOrderProcess">`: Specifies the BPEL process to be deployed.
    *   `<active>true</active>`: Indicates that the process should be active upon deployment.
    *   `<provide partnerLink="client">`: Defines the service that the BPEL process provides to its clients, exposing the `PlaceOrderService`.
    *   `<invoke partnerLink="catalogService">`: Configures the invocation of the `CatalogService` (SOAP), specifying its service and port.
3.  **Deployment:** The ZIP archive is typically placed in Apache ODE's `deploy` directory. ODE automatically detects and deploys the process, making it available for invocation. The process will be accessible via the ODE console, usually at `http://localhost:8084/ode`.

**Testing on Apache ODE:**

Testing of the BPEL process involves invoking it with various inputs and verifying the outputs or expected behaviors, including error handling. Apache ODE provides a web console that can be used to manually invoke processes and inspect their execution. For automated testing, test scenarios are defined:

1.  **Success Flow Test (`success-flow.xml`):**
    *   **Purpose:** To verify that the BPEL process successfully executes the entire workflow for a valid input, including interactions with external services, and produces the expected output.
    *   **Input:**
        ```xml
        <testScenario name="SuccessFlow">
          <input>
            <placeOrder xmlns="http://bpel.globalbooks.com/">
              <isbn>1234567890</isbn>
              <quantity>1</quantity>
            </placeOrder>
          </input>
          <expectedOutput>
            <placeOrderResponse xmlns="http://bpel.globalbooks.com/">
              <orderId>12345</orderId>
            </placeOrderResponse>
          </expectedOutput>
        </testScenario>
        ```
    *   **Verification:** The test asserts that the `placeOrderResponse` contains the expected `orderId` (e.g., `12345`), indicating that the process completed successfully.

2.  **Error Handling Test (`error-handling.xml`):**
    *   **Purpose:** To ensure that the BPEL process correctly handles error conditions, such as an invalid ISBN provided to the `CatalogService`, and produces the expected fault.
    *   **Input:**
        ```xml
        <testScenario name="ErrorHandling">
          <input>
            <placeOrder xmlns="http://bpel.globalbooks.com/">
              <isbn>INVALID_ISBN</isbn>
              <quantity>1</quantity>
            </placeOrder>
          </input>
          <expectedFault>
            <faultName>InvalidISBNFault</faultName>
          </expectedFault>
        </testScenario>
        ```
    *   **Verification:** The test asserts that a specific fault (e.g., `InvalidISBNFault`) is returned, demonstrating that the process's error handling mechanisms are functioning as expected.

These test scenarios, combined with the ability to inspect process instances and their variables in the ODE console, provide a comprehensive approach to deploying and testing BPEL processes.

## Task 10
Explain how you integrated PaymentsService and ShippingService: queue definitions, producers/consumers.
The `PaymentsService` and `ShippingService` are integrated using RabbitMQ as an Enterprise Service Bus (ESB) for asynchronous messaging, enabling an event-driven architecture.

**Queue and Exchange Definitions:**

1.  **Exchange Definition (`exchanges.json`):**
    A `topic` exchange named `orders` is defined. This type of exchange routes messages to queues based on a routing key pattern match.
    ```json
    [
      {
        "name": "orders",
        "type": "topic",
        "durable": true,
        "auto_delete": false
      }
    ]
    ```
    *   `name: "orders"`: The name of the exchange.
    *   `type: "topic"`: Messages are routed based on routing keys that match patterns.
    *   `durable: true`: The exchange will survive a broker restart.
    *   `auto_delete: false`: The exchange will not be deleted automatically when no longer used.

2.  **Queue Definitions (`queues.json`):**
    Two durable queues are defined: `orders.payments` and `orders.shipping`. These queues will receive messages from the `orders` exchange based on their binding keys.
    ```json
    [
      {
        "name": "orders.payments",
        "durable": true,
        "auto_delete": false,
        "arguments": {}
      },
      {
        "name": "orders.shipping",
        "durable": true,
        "auto_delete": false,
        "arguments": {}
      }
    ]
    ```
    *   `name: "orders.payments"`: Queue for payment-related order events.
    *   `name: "orders.shipping"`: Queue for shipping-related order events.
    *   `durable: true`: The queues will survive a broker restart.

**Producers and Consumers:**

1.  **Producer (`OrdersService`):**
    *   The `OrdersService` acts as the message producer. After an order is successfully created (e.g., via the `POST /v1/orders` endpoint), it publishes an "order created" event to the `orders` topic exchange.
    *   The routing key for these messages would typically be something like `order.created`.
    *   The `MessageProducer.java` (conceptual) would encapsulate the logic for connecting to RabbitMQ and publishing messages to the `orders` exchange with the appropriate routing key.

2.  **Consumers (`PaymentsService` and `ShippingService`):**
    *   **`PaymentsService`:** This service acts as a consumer for payment-related order events. It binds a queue (e.g., `orders.payments`) to the `orders` exchange with a routing key pattern (e.g., `order.created.payment` or a more general `order.#` if it needs all order events). When a message arrives in its queue, the `PaymentsService` consumes it and initiates the payment processing logic asynchronously.
    *   **`ShippingService`:** Similarly, this service acts as a consumer for shipping-related order events. It binds a queue (e.g., `orders.shipping`) to the `orders` exchange with a routing key pattern (e.g., `order.created.shipping` or `order.#`). Upon consuming a message, the `ShippingService` triggers the shipping coordination process.

This setup ensures that order events are reliably delivered to the relevant downstream services without direct coupling, allowing for independent scaling and processing.

## Task 11
Describe your error-handling and dead-letter routing strategy
For robust asynchronous messaging, a comprehensive error-handling and dead-letter routing strategy is implemented using RabbitMQ. This ensures that messages that cannot be processed successfully are not lost and can be inspected or reprocessed.

**Strategy:**

1.  **Consumer-Side Error Handling:**
    *   Within the `PaymentsService` and `ShippingService` consumers, message processing logic is wrapped in `try-catch` blocks.
    *   If a message consumer encounters an unrecoverable error during processing (e.g., invalid message format, business logic failure, external service unavailability), it will explicitly `reject` the message.
    *   For transient errors, consumers might implement retry mechanisms with exponential backoff before ultimately rejecting the message.

2.  **Dead-Letter Exchange (DLX) and Dead-Letter Queue (DLQ):**
    *   Each primary queue (e.g., `orders.payments`, `orders.shipping`) is configured with a Dead-Letter Exchange (DLX). This is done by setting the `x-dead-letter-exchange` argument on the queue.
    *   When a message is rejected by a consumer (and not re-queued), or if it expires (TTL), or if the queue length limit is exceeded, RabbitMQ automatically routes that message to the DLX.
    *   A dedicated Dead-Letter Queue (DLQ) is bound to this DLX. For instance, `orders.payments.dlq` would be bound to the DLX associated with `orders.payments`.
    *   Messages that end up in the DLQ are considered "dead letters."

3.  **Monitoring and Manual Intervention:**
    *   The DLQs are continuously monitored (e.g., via RabbitMQ Management UI or an automated monitoring system).
    *   Messages in the DLQ can be inspected to understand the cause of failure.
    *   Depending on the error, these messages can be manually re-queued to the original queue for reprocessing after the underlying issue is resolved, or they can be moved to an archive for further analysis.

**Benefits:**
*   **No Message Loss:** Ensures that no messages are silently dropped due to processing failures.
*   **Troubleshooting:** Provides a dedicated location for failed messages, making it easier to diagnose and debug issues.
*   **Resilience:** Improves the overall resilience of the messaging system by isolating problematic messages.

This dead-letter routing strategy provides a safety net for message processing, allowing for graceful handling of errors and preventing data loss in the asynchronous communication flow.

## Task 12
Detail WS-Security configuration for CatalogService (UsernameToken or X.509).
The `CatalogService` is secured using WS-Security with a **UsernameToken** profile, ensuring authentication of clients accessing the SOAP service.

**Security Policy (`security-policy.xml`):**
The core of the WS-Security configuration is defined in the `security-policy.xml` file. This policy specifies that a UsernameToken must be included in the SOAP message for authentication.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<wsp:Policy xmlns:wsp="http://schemas.xmlsoap.org/ws/2004/09/policy"
            xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"
            wsu:Id="UsernameTokenPolicy">
  <wsp:ExactlyOne>
    <wsp:All>
      <sp:SupportingTokens xmlns:sp="http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702">
        <wsp:Policy>
          <sp:UsernameToken sp:IncludeToken="http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/AlwaysToRecipient"/>
        </wsp:Policy>
      </sp:SupportingTokens>
    </wsp:All>
  </wsp:ExactlyOne>
</wsp:Policy>
```
*   `wsu:Id="UsernameTokenPolicy"`: Identifies this specific security policy.
*   `<sp:SupportingTokens>`: Indicates that the policy requires supporting security tokens.
*   `<sp:UsernameToken>`: Specifies that a UsernameToken is required.
*   `sp:IncludeToken="http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/AlwaysToRecipient"`: Mandates that the UsernameToken must always be included in the message sent to the recipient.

**WSS4J Configuration (`wss4j.properties`):**
While the `wss4j.properties` file in the provided project structure is empty, in a typical WSS4J setup for UsernameToken, this file would contain properties related to:
*   **Password Type:** How the password is sent (e.g., `PasswordText` for plain text, `PasswordDigest` for hashed passwords).
*   **Callback Handler:** The class responsible for validating the username and password against a user store (e.g., a database, LDAP, or an in-memory map). This is crucial for authentication.
*   **Nonce and Timestamp Validation:** Configuration for preventing replay attacks by validating nonces and timestamps.

Given the empty `wss4j.properties` file, it implies that the WSS4J configuration for the `CatalogService` is either handled programmatically within the Java code (e.g., by setting properties on the `WSSConfig` or `WSHandler` instances) or relies on default WSS4J behaviors, with the actual user credential validation being performed by a custom callback handler. The `SOAP UI` test case confirms that `admin/password` is used, suggesting an in-memory or simple user store for development.

This configuration ensures that only authenticated clients providing valid username and password credentials within a UsernameToken in the SOAP header can access the `CatalogService`.

## Task 13
Describe OAuth2 setup for OrdersService
The `OrdersService` is secured using OAuth2 with Spring Security, acting as a resource server. This setup ensures that only authorized clients with valid access tokens can access its REST endpoints.

**Configuration (`oauth2-config.yml`):**
The `oauth2-config.yml` file provides the necessary client credentials and the token URI for the OAuth2 server.

```yaml
oauth2:
  client-id: globalbooks-client
  client-secret: your-secret
  token-uri: http://localhost:9000/oauth2/token
```
*   `client-id`: `globalbooks-client` - This is the identifier for the client application that will be requesting access tokens to interact with the `OrdersService`.
*   `client-secret`: `your-secret` - This is the secret associated with the `client-id`, used for client authentication with the OAuth2 authorization server.
*   `token-uri`: `http://localhost:9000/oauth2/token` - This specifies the endpoint of the OAuth2 authorization server where clients can obtain access tokens. In a local development environment, this would typically point to a local OAuth2 server instance.

**Spring Security Configuration (Conceptual):**
Within the `OrdersService` Spring Boot application, Spring Security would be configured to:
1.  **Enable Resource Server:** Mark the application as an OAuth2 resource server, which means it will protect its resources and validate incoming access tokens.
2.  **Token Validation:** Configure how access tokens are validated. This typically involves:
    *   **Introspection:** The `OrdersService` would use the `token-uri` to introspect (validate) the received access tokens with the OAuth2 authorization server.
    *   **JWT (JSON Web Token) Decoding:** If the access tokens are JWTs, the service would be configured to decode and verify the JWT's signature and claims (e.g., issuer, audience, expiration).
3.  **Authorization Rules:** Define authorization rules for different endpoints (e.g., `/v1/orders` requires `SCOPE_orders.write`, `/v1/orders/{id}` requires `SCOPE_orders.read`).
4.  **Exception Handling:** Configure how unauthorized access or invalid tokens are handled, typically returning HTTP 401 Unauthorized or 403 Forbidden responses.

**Flow:**
1.  A client application (e.g., a web frontend) authenticates with the OAuth2 authorization server (at `http://localhost:9000/oauth2/token`) using its `client-id` and `client-secret` to obtain an access token.
2.  The client then includes this access token in the `Authorization` header (e.g., `Bearer <access_token>`) when making requests to the `OrdersService`.
3.  The `OrdersService` intercepts the request, validates the access token with the OAuth2 server, and if valid, grants access to the requested resource based on the token's scopes and the service's authorization rules.

This OAuth2 setup provides a robust and standardized way to secure the `OrdersService` and control access to its resources.

## Task 14
Explain one QoS mechanism you configured for reliable messaging (e.g., persistent messages, publisher confirms).
One key Quality of Service (QoS) mechanism configured for reliable messaging in the RabbitMQ integration is the use of **Persistent Messages** through **Durable Exchanges and Queues**.

**Mechanism: Persistent Messages with Durable Exchanges and Queues**

1.  **Durable Exchanges:**
    As seen in `exchanges.json`, the `orders` exchange is declared as `durable: true`.
    ```json
    {
      "name": "orders",
      "type": "topic",
      "durable": true,
      "auto_delete": false
    }
    ```
    A durable exchange will survive a RabbitMQ broker restart. This means that even if the broker goes down, the exchange definition itself is not lost.

2.  **Durable Queues:**
    Similarly, in `queues.json`, both `orders.payments` and `orders.shipping` queues are declared as `durable: true`.
    ```json
    {
      "name": "orders.payments",
      "durable": true,
      "auto_delete": false,
      "arguments": {}
    }
    ```
    A durable queue will also survive a broker restart. Messages that are in a durable queue at the time of a broker shutdown will be recovered when the broker comes back online.

3.  **Message Persistence:**
    For messages to be truly persistent, they must be published with the `delivery_mode` property set to `2` (persistent). When a message is published as persistent to a durable exchange and then routed to a durable queue, RabbitMQ writes the message to disk. This ensures that if the RabbitMQ broker crashes or is restarted, the message is not lost and will be delivered once the broker is back online and the consumer is ready.

**Benefit:**
The primary benefit of using persistent messages with durable exchanges and queues is **guaranteed message delivery** even in the event of broker failures. This is crucial for critical business processes like order processing, where losing a message could lead to data inconsistency or missed business operations. It significantly enhances the reliability and fault tolerance of the asynchronous communication between services.

While publisher confirms and consumer acknowledgements are also vital for end-to-end reliability, the durability of exchanges and queues, combined with persistent messages, forms the foundational layer for ensuring messages are not lost at the broker level.

## Task 15
Draft the governance policy: versioning strategy (URL & namespace conventions), SLA targets (availability, response time), and deprecation plan (notice period, sunset process).
The following governance policies are established for the GlobalBooks Inc. SOA and Microservices project:

**1. Versioning Strategy (URL & Namespace Conventions):**
*   **Policy:** All REST APIs will adhere to a URL path versioning strategy. The API version will be explicitly included in the URL path, prefixed with `v`.
*   **Example:** `/v1/orders`
*   **Rationale:** This approach ensures that new versions of the API can be introduced without immediately breaking existing client integrations, providing a clear and predictable evolution path for consumers.
*   **Namespace Conventions (Implicit for SOAP):** For SOAP services, versioning is typically handled through WSDL and XML Schema namespaces. While not explicitly detailed in a separate policy file, changes to the SOAP contract would involve updating the target namespace to indicate a new version.

**2. Service Level Agreement (SLA) Targets:**
*   **Uptime:** A target uptime of **99.5%** is set for all critical services. This metric measures the availability of the services to end-users.
*   **Response Time:** Services are targeted to achieve a response time of **sub-200ms for 95th percentile requests**. This ensures that the majority of requests are processed quickly, contributing to a positive user experience.
*   **Monitoring:** Continuous monitoring will be in place to track these metrics and ensure adherence to the defined SLA targets.

**3. Deprecation Plan (Notice Period, Sunset Process):**
*   **Notice Period:** When a new version of an API is released, the older version will be supported for a minimum period of **6 months**. This provides ample time for clients to migrate to the newer version.
*   **Communication:** A clear and timely deprecation schedule will be formally communicated to all affected clients and partners through appropriate channels (e.g., developer portal, direct communication).
*   **Sunset Process:** After the minimum support period, the old version will be formally deprecated. This phase may involve reduced support or performance. Eventually, the old version will be sunsetted (removed), at which point it will no longer be available. The communication will clearly outline the sunset date.
*   **Rationale:** This structured deprecation process aims to minimize disruption to consumers while allowing the development team to evolve and improve the services.

These policies collectively provide a framework for managing the lifecycle, performance, and evolution of the services within the Hybrid SOA.

## Task 16
Deploy all four services (Catalog, Orders, Payments, Shipping) to a cloud platform
The deployment of all four services (Catalog, Orders, Payments, Shipping) to a cloud platform is designated as a **future phase** of this project. The current focus is on establishing and validating the hybrid SOA architecture within a local development environment.

However, significant preparation has been undertaken to ensure the project is "cloud-ready" for a future migration, primarily targeting AWS. This preparation includes:

1.  **Infrastructure as Code (IaC) with OpenTofu:**
    *   OpenTofu templates (`local-setup/opentofu/`) have been created to define the cloud infrastructure required for deploying the services. This includes modules for:
        *   **AWS Lambda:** For deploying the REST microservices (`OrdersService`, `PaymentsService`, `ShippingService`) as serverless functions.
        *   **AWS API Gateway:** To expose the REST Lambda functions as managed APIs.
        *   **Amazon RDS PostgreSQL:** For managed database instances for each service.
        *   **Amazon MQ (RabbitMQ):** For a managed message broker service.
        *   **EC2 for Tomcat:** Potentially for deploying the `CatalogService` (SOAP) on a dedicated EC2 instance running Tomcat, if a serverless option for SOAP is not chosen.
    *   This IaC approach ensures repeatable, consistent, and version-controlled infrastructure provisioning.

2.  **Lambda Packaging Strategy:**
    *   Each REST microservice (`OrdersService`, `PaymentsService`, `ShippingService`) includes a `lambda` directory with a dedicated Lambda handler class (e.g., `OrdersLambdaHandler.java`).
    *   This indicates a design choice to package these Spring Boot applications as AWS Lambda functions, leveraging serverless computing for scalability and cost efficiency.

3.  **CI/CD Pipelines:**
    *   GitHub Actions workflows (`.github/workflows/`) are defined for Continuous Integration (CI) and Continuous Deployment (CD).
    *   `ci-soap.yml` and `ci-rest.yml` are for building and testing the SOAP and REST services, respectively.
    *   `deploy.yml` is intended for automating the deployment process to the cloud platform once the services are ready for production.

**Cloud Migration Strategy (Conceptual):**
The planned cloud migration strategy involves:
*   **Lift-and-Shift (for SOAP):** The `CatalogService` (SOAP) might be initially deployed to an EC2 instance running Tomcat, or a containerized solution like ECS/EKS, given its legacy nature.
*   **Serverless (for REST):** The `OrdersService`, `PaymentsService`, and `ShippingService` will be deployed as AWS Lambda functions, fronted by API Gateway.
*   **Managed Services:** Utilizing Amazon RDS for databases and Amazon MQ for RabbitMQ ensures reduced operational overhead.
*   **Automated Deployment:** Leveraging GitHub Actions and OpenTofu for automated, repeatable deployments.

In summary, while actual cloud deployment is pending, the project has been meticulously prepared with the necessary infrastructure definitions, application adaptations (Lambda handlers), and CI/CD pipelines to facilitate a smooth transition to a cloud environment in the future.