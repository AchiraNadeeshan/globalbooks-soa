# AWS Deployment Guide (Future Phase)

This document outlines the strategy and considerations for migrating the GlobalBooks Inc. SOA and Microservices project to AWS. This is a future phase and the current implementation focuses on local development.

## 1. Cloud Migration Strategy

-   **Hybrid Approach:** Continue with a hybrid approach, leveraging AWS services for scalability and managed solutions while potentially maintaining some on-premises components if required.
-   **Phased Migration:** Migrate services incrementally, starting with REST microservices, followed by messaging and database, and finally the SOAP service if necessary.

## 2. OpenTofu Infrastructure Templates

OpenTofu (formerly Terraform) will be used for Infrastructure as Code (IaC) to provision and manage AWS resources. Templates are located in `local-setup/opentofu/`.

-   **`modules/lambda/main.tf`:** Defines AWS Lambda functions for deploying REST microservices.
-   **`modules/api-gateway/main.tf`:** Configures AWS API Gateway for exposing REST microservices.
-   **`modules/rds/main.tf`:** Provisions Amazon RDS instances for PostgreSQL databases.
-   **`modules/rabbitmq/main.tf`:** Sets up Amazon MQ for RabbitMQ.
-   **`modules/ec2-tomcat/main.tf`:** Defines EC2 instances for potential Tomcat deployment (if SOAP service is migrated to AWS).

## 3. Lambda Packaging Strategy for REST Services

-   REST microservices will be packaged as AWS Lambda functions.
-   Each Spring Boot application will have a dedicated Lambda handler (e.g., `OrdersLambdaHandler.java`).
-   The Lambda functions will be deployed as `.jar` files, including all necessary dependencies.

## 4. CI/CD Pipeline Design for Cloud Deployment

GitHub Actions will be used to automate the CI/CD pipeline for cloud deployments.

-   **`ci-soap.yml`:** Continuous Integration for the SOAP service (build and test).
-   **`ci-rest.yml`:** Continuous Integration for REST microservices (build and test).
-   **`deploy.yml`:** Continuous Deployment workflow for deploying services to AWS.

## 5. RDS Migration Strategy for Databases

-   Migrate local PostgreSQL databases to Amazon RDS for PostgreSQL.
-   Utilize AWS Database Migration Service (DMS) for data migration.
-   Configure RDS instances for high availability and backups.

## 6. Amazon MQ Migration for RabbitMQ

-   Migrate local RabbitMQ instance to Amazon MQ for RabbitMQ.
-   Ensure compatibility with existing Spring AMQP configurations.
-   Leverage Amazon MQ's managed service for scalability and reliability.

## 7. Monitoring in AWS

-   **AWS CloudWatch:** For collecting and tracking metrics, collecting and monitoring log files, and setting alarms.
-   **AWS X-Ray:** For tracing requests across distributed services.
-   **Amazon Managed Service for Prometheus (AMP) & Amazon Managed Grafana:** For advanced metrics and visualization.