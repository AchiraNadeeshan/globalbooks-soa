# Task Completion

## Task 1
Explain which SOA design principles you applied when decomposing the monolith into independent services.

## Task 2
Discuss one key benefit and one primary challenge of your approach.

## Task 3
Provide a WSDL excerpt for the CatalogService (operations, types, binding)

## Task 4
Draft the UDDI registry entry metadata enabling client discovery.

## Task 5
Describe in detail how you implemented the CatalogService SOAP endpoint in Java (including sun-jaxws.xml and web.xml snippets).

## Task 6
Explain how you tested it using SOAP UI (test cases and assertions).

## Task 7
Design the OrdersService REST API: list endpoints (POST /orders, GET /orders/{id}), sample JSON request & response, and the JSON Schema for order creation.

## Task 8
Outline the "PlaceOrder" BPEL process: receive, loop for price lookup via CatalogService, invoke OrdersService, reply to client.

## Task 9
Explain deployment and testing on a BPEL engine (e.g., Apache ODE).

## Task 10
Explain how you integrated PaymentsService and ShippingService: queue definitions, producers/consumers.

## Task 11
Describe your error-handling and dead-letter routing strategy

## Task 12
Detail WS-Security configuration for CatalogService (UsernameToken or X.509).

## Task 13
Describe OAuth2 setup for OrdersService

## Task 14
Explain one QoS mechanism you configured for reliable messaging (e.g., persistent messages, publisher confirms).

## Task 15
Draft the governance policy: versioning strategy (URL & namespace conventions), SLA targets (availability, response time), and deprecation plan (notice period, sunset process).

## Task 16
Deploy all four services (Catalog, Orders, Payments, Shipping) to a cloud platform