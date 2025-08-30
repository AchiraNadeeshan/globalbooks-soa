# Shipping REST API

This document describes the REST API for the `ShippingService`, which handles the coordination of shipping processes. This service primarily operates asynchronously, consuming messages from RabbitMQ.

## Base URL

`http://localhost:8083`

## Asynchronous Processing

-   The `ShippingService` acts as a **RabbitMQ consumer**. It listens for "order created" events published by the `OrdersService`.
-   Upon receiving an order event, the `ShippingService` initiates the shipping process asynchronously.
-   There are no direct REST endpoints for initiating shipping from external clients; shipping processes are driven by internal system events.

## Health Check Endpoint

### `GET /health`

Provides a basic health check for the service.

-   **Example Request (Postman):**

    ```
    GET http://localhost:8083/health
    ```

-   **Example Response:**

    ```json
    {
        "status": "UP"
    }
    ```

## RabbitMQ Integration

-   **Exchange:** `orders` (topic exchange)
-   **Queue:** `orders.shipping`
-   **Routing Key:** `order.created`

Messages containing order details are consumed from the `orders.shipping` queue.