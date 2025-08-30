# Messaging Flow Scenarios

This document details the asynchronous messaging flow scenarios within the GlobalBooks Inc. Hybrid SOA, primarily utilizing RabbitMQ as the Enterprise Service Bus (ESB).

## 1. Order Creation Event Propagation

**Objective:** Demonstrate how the creation of an order in the `OrdersService` triggers asynchronous processing in the `PaymentsService` and `ShippingService` via RabbitMQ.

-   **Flow:**
    1.  A client (e.g., via Postman) sends a `POST` request to `OrdersService` to create a new order.
    2.  `OrdersService` processes the order and persists it to its database.
    3.  `OrdersService` then publishes an "order.created" event to the `orders` topic exchange in RabbitMQ with the routing key `order.created`.
    4.  The `orders` exchange routes the message to two queues:
        -   `orders.payments` (consumed by `PaymentsService`)
        -   `orders.shipping` (consumed by `ShippingService`)
    5.  `PaymentsService` consumes the message from `orders.payments` and initiates payment processing.
    6.  `ShippingService` consumes the message from `orders.shipping` and initiates shipping coordination.

-   **Key Components:**
    -   `OrdersService` (Spring Boot): Message Producer
    -   `PaymentsService` (Spring Boot): Message Consumer
    -   `ShippingService` (Spring Boot): Message Consumer
    -   RabbitMQ: Message Broker, configured with `orders` topic exchange and `orders.payments`, `orders.shipping` queues.

-   **Verification Steps:**
    1.  Ensure RabbitMQ server is running and accessible via Management UI (`http://localhost:15672`).
    2.  Observe the `orders` exchange and the two queues in the RabbitMQ Management UI before and after creating an order.
    3.  Check the console logs of `PaymentsService` and `ShippingService` to confirm message reception and processing.

## 2. Error Handling with Dead Letter Queues (Future Enhancement)

**Objective:** (Conceptual) Illustrate how messages that fail processing can be routed to a Dead Letter Queue (DLQ) for later inspection and reprocessing.

-   **Flow (Conceptual):**
    1.  A message is sent to a primary queue (e.g., `orders.payments`).
    2.  The consumer in `PaymentsService` fails to process the message (e.g., due to invalid data, external service unavailability).
    3.  After a configured number of retries, the message is automatically moved to a `dead-letter-exchange` with a specific `dead-letter-routing-key`.
    4.  The `dead-letter-exchange` routes the message to a `dead-letter-queue`.
    5.  Operators can inspect messages in the DLQ to diagnose issues and manually reprocess them if necessary.

-   **Key Concepts:** Dead Letter Exchange (DLX), Dead Letter Queue (DLQ), message retries.
-   **Benefit:** Prevents message loss, provides a mechanism for error recovery, and improves system robustness.
