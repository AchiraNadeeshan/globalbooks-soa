# Catalog SOAP API

This document describes the SOAP API for the `CatalogService`, which provides book catalog information to legacy client systems.

## Service Endpoint

`http://localhost:8080/catalog-service/CatalogService`

## WSDL

The WSDL (Web Services Description Language) for the service is available at:

`http://localhost:8080/catalog-service/CatalogService?wsdl`

## Operations

### `getBookDetails`

Retrieves details of a book based on its ISBN.

-   **Input Message:** `GetBookDetailsRequest`
    -   `isbn` (xsd:string): The International Standard Book Number of the book.

-   **Output Message:** `GetBookDetailsResponse`
    -   `book` (complexType):
        -   `isbn` (xsd:string): The ISBN of the book.
        -   `title` (xsd:string): The title of the book.
        -   `author` (xsd:string): The author of the book.
        -   `price` (xsd:double): The price of the book.

## Security

This service is secured using **WS-Security UsernameToken**. Clients must include a `UsernameToken` in the SOAP header for authentication. The expected username is `admin` and the password is `password` (for local development).

### Example SOAP Request (SOAP UI)

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://catalog.globalbooks.com/">
   <soapenv:Header>
      <wsse:Security soapenv:mustUnderstand="1" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="UsernameToken" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">password</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <cat:getBookDetails>
         <isbn>1234567890</isbn>
      </cat:getBookDetails>
   </soapenv:Body>
</soapenv:Envelope>
```

### Example SOAP Response

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:getBookDetailsResponse xmlns:ns2="http://catalog.globalbooks.com/">
         <book>
            <isbn>1234567890</isbn>
            <title>The Lord of the Rings</title>
            <author>J.R.R. Tolkien</author>
            <price>25.99</price>
         </book>
      </ns2:getBookDetailsResponse>
   </soap:Body>
</soap:Envelope>
```