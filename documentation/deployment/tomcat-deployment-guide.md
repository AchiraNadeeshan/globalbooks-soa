# Tomcat Deployment Guide

This guide provides instructions for deploying the `catalog-service` (SOAP service) to a local Apache Tomcat 10.x server.

## Prerequisites

-   Apache Tomcat 10.x installed and configured.
-   `catalog-service.war` file built and available in `services/catalog-service/target/`.

## Deployment Steps

1.  **Stop Tomcat:** Ensure your Tomcat server is stopped before deployment.

    ```bash
    # Navigate to your Tomcat bin directory
    cd /path/to/apache-tomcat-10.x/bin
    ./shutdown.sh # Linux/macOS
    # Or
    shutdown.bat # Windows
    ```

2.  **Copy WAR file:** Copy the `catalog-service.war` file to Tomcat's `webapps` directory.

    ```bash
    cp D:\PROJECTS\Java\globalbooks-soa\services\catalog-service\target\catalog-service-1.0.0.war /path/to/apache-tomcat-10.x/webapps/catalog-service.war
    ```

3.  **Start Tomcat:** Start the Tomcat server.

    ```bash
    # Navigate to your Tomcat bin directory
    cd /path/to/apache-tomcat-10.x/bin
    ./startup.sh # Linux/macOS
    # Or
    startup.bat # Windows
    ```

4.  **Verify Deployment:** Once Tomcat starts, the `catalog-service.war` will be automatically unpacked and deployed. You can verify the deployment by accessing the WSDL in your browser:

    `http://localhost:8080/catalog-service/CatalogService?wsdl`

    You should see the WSDL definition in your browser.

## Configuration Notes

-   **Port:** Ensure Tomcat is configured to run on port `8080` as specified in the `.env` file and `catalog.wsdl`.
-   **WS-Security:** The `catalog-service` is configured with WS-Security UsernameToken. Ensure your client requests include the necessary security headers.