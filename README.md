## Reactive Microservices-Based Order Management System

This project consists of a suite of reactive backend microservices designed to handle order placement within an online webstore.

![Architecture Diagram](/architecture.png)

When a create order request is received by the Order Service, multiple microservices activate by responding to the relevant events. The architecture shown includes six distinct services:

-Order Service: Manages creation and updates of orders.
-Warehouse Service: Handles stock reservation and release, and generates shipment records.
-Finance Service: Processes payments and generates invoices.
-Catalogue Service: Stores product information including details and pricing.
-Notification Service: Sends customer notifications regarding order status updates.
-Customer Service: Maintains customer data such as names and contact details.

Each microservice focuses on a single responsibility and operates through event-driven communication. Services remain loosely coupled and do not have direct knowledge of each other.

## Technologies

- Spring Webflux
- Spring Kafka
- Spring Reactive Mongo
- MongoDB
- Kafka
- Zookeeper
- Docker
