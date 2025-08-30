# BPEL Orchestration Scenarios

This document outlines the BPEL orchestration scenarios implemented in the GlobalBooks Inc. project, focusing on the `PlaceOrderProcess`.

## 1. PlaceOrderProcess: Success Flow

**Objective:** Successfully process a new order by retrieving book details from the `CatalogService` (SOAP) and then creating the order via the `OrdersService` (REST).

-   **Input:** A request to place an order with a valid ISBN and quantity.

    ```xml
    <placeOrder xmlns="http://bpel.globalbooks.com/">
      <isbn>1234567890</isbn>
      <quantity>1</quantity>
    </placeOrder>
    ```

-   **Expected Flow:**
    1.  BPEL process receives the `placeOrder` request.
    2.  Invokes the `CatalogService` (SOAP) with the provided ISBN.
    3.  Receives book details (title, author, price) from `CatalogService`.
    4.  (Future Implementation) Constructs a request for `OrdersService` (REST) with book details and quantity.
    5.  (Future Implementation) Invokes `OrdersService` (REST) to create the order.
    6.  Receives a successful order creation response (e.g., order ID) from `OrdersService`.
    7.  Returns a `placeOrderResponse` with the generated order ID.

-   **Expected Output:** A response indicating successful order placement with an order ID.

    ```xml
    <placeOrderResponse xmlns="http://bpel.globalbooks.com/">
      <orderId>12345</orderId>
    </placeOrderResponse>
    ```

## 2. PlaceOrderProcess: Error Handling Scenario

**Objective:** Demonstrate how the BPEL process handles errors, specifically when an invalid ISBN is provided to the `CatalogService`.

-   **Input:** A request to place an order with an invalid ISBN.

    ```xml
    <placeOrder xmlns="http://bpel.globalbooks.com/">
      <isbn>INVALID_ISBN</isbn>
      <quantity>1</quantity>
    </placeOrder>
    ```

-   **Expected Flow:**
    1.  BPEL process receives the `placeOrder` request.
    2.  Invokes the `CatalogService` (SOAP) with the invalid ISBN.
    3.  `CatalogService` returns a SOAP Fault (or an application-specific error).
    4.  BPEL process catches the fault/error.
    5.  (Future Implementation) Performs error compensation or notifies an error handling system.
    6.  Returns a fault response to the client.

-   **Expected Fault Output:** A SOAP Fault indicating an invalid ISBN.

    ```xml
    <!-- Example of a potential SOAP Fault response -->
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
       <soapenv:Body>
          <soapenv:Fault>
             <faultcode>soapenv:Client</faultcode>
             <faultstring>Invalid ISBN provided</faultstring>
             <detail>
                <ns1:InvalidISBNFault xmlns:ns1="http://catalog.globalbooks.com/">
                   <message>The provided ISBN is not valid or not found.</message>
                </ns1:InvalidISBNFault>
             </detail>
          </soapenv:Fault>
       </soapenv:Body>
    </soapenv:Envelope>
    ```
