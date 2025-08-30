# Payments REST API

This document describes the REST API for the `PaymentsService`, which processes payment transactions. This service primarily operates asynchronously, consuming messages from RabbitMQ.

## Base URL

`http://localhost:8082`

## Asynchronous Processing

-   The `PaymentsService` acts as a **RabbitMQ consumer**. It listens for "order created" events published by the `OrdersService`.
-   Upon receiving an order event, the `PaymentsService` processes the payment asynchronously.
-   There are no direct REST endpoints for initiating payments from external clients; payments are driven by internal system events.

## Health Check Endpoint

### `GET /health`

Provides a basic health check for the service.

-   **Example Request (Postman):**

    ```
    GET http://localhost:8082/health
    ```

-   **Example Response:**

    ```json
    {
        "status": "UP"
    }
    ```

## RabbitMQ Integration

-   **Exchange:** `orders` (topic exchange)
-   **Queue:** `orders.payments`
-   **Routing Key:** `order.created`

Messages containing order details are consumed from the `orders.payments` queue.