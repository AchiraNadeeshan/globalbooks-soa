# Orders REST API

This document describes the REST API for the `OrdersService`, which handles the creation and management of customer orders for modern clients.

## Base URL

`http://localhost:8081/v1/orders`

## Authentication

This API is secured using **OAuth2**. Clients must obtain an OAuth2 token (Bearer Token) and include it in the `Authorization` header of their requests.

## Endpoints

### `POST /v1/orders`

Creates a new order.

-   **Request Body (JSON):**

    ```json
    {
        "isbn": "string",
        "quantity": integer
    }
    ```

    -   `isbn` (string, required): The ISBN of the book to order.
    -   `quantity` (integer, required): The quantity of the book to order (must be at least 1).

-   **Response Body (JSON):**

    ```json
    {
        "id": long,
        "isbn": "string",
        "quantity": integer
    }
    ```

    -   `id` (long): The unique identifier of the created order.
    -   `isbn` (string): The ISBN of the ordered book.
    -   `quantity` (integer): The quantity of the ordered book.

-   **Example Request (Postman):

    ```
    POST http://localhost:8081/v1/orders
    Content-Type: application/json
    Authorization: Bearer YOUR_OAUTH2_TOKEN

    {
        "isbn": "1234567890",
        "quantity": 1
    }
    ```

-   **Example Response:**

    ```json
    {
        "id": 1,
        "isbn": "1234567890",
        "quantity": 1
    }
    ```

## Asynchronous Processing

Upon successful order creation, the `OrdersService` publishes an event to RabbitMQ. The `PaymentsService` and `ShippingService` consume these events asynchronously to process payments and manage shipping, respectively.