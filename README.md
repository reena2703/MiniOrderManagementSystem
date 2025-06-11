# Mini Order Management System

A Java-based REST API backend for a Mini E-commerce Order Management System, built with **Spring Boot** and integrated with **MongoDB**. This system leverages embedded document modeling, supports full CRUD operations, query-based filtering, and includes MongoDB-specific data handling techniques.

## 🎯 Objective Achieved

This project fulfills the assignment requirements by developing a REST API for an Order Management System with the following capabilities:

*   **Embedded Document Modeling:** Utilizes embedded documents for `Customer` and `OrderItem` within the `Order` entity.
*   **Full CRUD Operations:** Supports creation, retrieval, update, and deletion of Customers, Products, and Orders.
*   **Query-Based Filtering:** Implements endpoints for searching orders by customer email/phone, total amount, and date range.
*   **MongoDB-specific Data Handling:** Demonstrates effective use of Spring Data MongoDB for seamless integration.

## 🧩 Problem Statement

Developed a Java-based REST API backend for a Mini E-commerce Order Management System integrated with MongoDB. The system stores data using embedded document models, supports core business operations, and exposes REST endpoints for interaction.

## 🧾 Entities

### 1. Customer
```java
String id;         // MongoDB ObjectId
String name;
String email;      // Indexed for unique email
String phone;
String address;
```

### 2. Product
```java
String id;
String name;
String category;
double price;
```

### 3. Order
```java
String id;
Customer customer;         // Embedded document (snapshot of customer)
List<OrderItem> items;     // Embedded documents (snapshot of products in order)
Date orderDate;
double totalAmount;        // Auto-calculated
Date createdAt;            // Timestamp
Date updatedAt;            // Timestamp
```

### 4. OrderItem
```java
Product product;           // Embedded product snapshot (price at time of order)
int quantity;
double subtotal;           // Auto-calculated
```

## 🔧 Functional Requirements Implemented

*   **Add new customers, products, and orders** via REST API.
*   **List all orders** including customer and item details.
*   **Search orders by customer email or phone.**
*   **Auto-calculate and store `totalAmount`** during order creation.
*   **Update a product’s price** (future orders reflect new price, past orders retain original).
*   **Delete a customer and cascade delete their associated orders.**
*   **Handle timestamps** for order creation (`createdAt`/`updatedAt`).

## 🗃️ Data Modeling Requirements

*   Used embedded documents for:
    *   Customer (inside Order)
    *   List of Order Items (inside Order)
*   Store data in MongoDB collections: `customers`, `products`, `orders`.

## 🚀 Bonus Features (Optional - Mention if you implemented any)

*   **Indexes:** Implemented `@Indexed(unique = true)` on `Customer.email`.
*   *(Add any other bonus features you implemented, e.g., pagination, invoice generation, unit tests)*

## 📦 Deliverables

*   ✅ Spring Boot Project (This repository)
*   ✅ README file (This file)
*   ✅ (Optional) 2–5 minute video walkthrough (Link to video, once uploaded)

## 🛠️ Technologies Used

*   **Java 17+**
*   **Spring Boot 3.x**
*   **Spring Data MongoDB**
*   **MongoDB**

## ⚙️ How to Run the Project

1.  **Prerequisites:**
    *   Java Development Kit (JDK) 17 or higher
    *   Apache Maven
    *   MongoDB Community Server (running on default port 27017 or configured in `application.properties`)

2.  **Clone the repository:**
    ```bash
    git clone <your-github-repo-link>
    cd mini-order-management
    ```

3.  **Configure MongoDB:**
    *   Ensure MongoDB is running.
    *   Open `src/main/resources/application.properties` and verify the MongoDB connection URI:
        ```properties
        spring.data.mongodb.uri=mongodb://localhost:27017/orderdb
        spring.data.mongodb.database=orderdb
        ```

4.  **Run the Spring Boot application:**
    *   **Using Maven:**
        ```bash
        mvn spring-boot:run
        ```
    *   **From IntelliJ IDEA:**
        *   Open the project in IntelliJ.
        *   Navigate to `src/main/java/com/example/MiniOrderManagement/MiniOrderManagementApplication.java`.
        *   Right-click on the `main` method and select "Run 'MiniOrderManagementApplication.main()'".

5.  **API Base URL:**
    *   The API will be accessible at `http://localhost:8080/api/`

## 📊 Sample Input/Output JSON

### **1. Customer**
*   **Create Customer (POST /api/customers)**
    *   **Request:**
        ```json
        {
          "name": "Alice Smith",
          "email": "alice@example.com",
          "phone": "1234567890",
          "address": "123 Main St, City"
        }
        ```
    *   **Response (example ID):**
        ```json
        {
          "id": "665c0c9d7d4c1b2f3a4e5b6c",
          "name": "Alice Smith",
          "email": "alice@example.com",
          "phone": "1234567890",
          "address": "123 Main St, City"
        }
        ```

### **2. Product**
*   **Create Product (POST /api/products)**
    *   **Request:**
        ```json
        {
          "name": "Wireless Mouse",
          "category": "Electronics",
          "price": 25.99
        }
        ```
    *   **Response (example ID):**
        ```json
        {
          "id": "665c0c9d7d4c1b2f3a4e7d8e",
          "name": "Wireless Mouse",
          "category": "Electronics",
          "price": 25.99
        }
        ```
*   **Update Product Price (PUT /api/products/{id})**
    *   **Request (using example ID above):**
        ```json
        {
          "id": "665c0c9d7d4c1b2f3a4e7d8e",
          "name": "Wireless Mouse",
          "category": "Electronics",
          "price": 35.00
        }
        ```

### **3. Order**
*   **Create Order (POST /api/orders)**
    *   **Request (replace IDs with actual ones from your database):**
        ```json
        {
          "customer": {
            "id": "CUSTOMER_ID_FROM_DB"
          },
          "items": [
            {
              "product": { "id": "PRODUCT_ID_FROM_DB" },
              "quantity": 2
            }
          ]
        }
        ```
    *   **Response (example, with embedded data and auto-calculated fields):**
        ```json
        {
          "id": "665c0c9d7d4c1b2f3a4e9f0a",
          "customer": {
            "id": "CUSTOMER_ID_FROM_DB",
            "name": "Alice Smith",
            "email": "alice@example.com",
            "phone": "1234567890",
            "address": "123 Main St, City"
          },
          "items": [
            {
              "product": {
                "id": "PRODUCT_ID_FROM_DB",
                "name": "Wireless Mouse",
                "category": "Electronics",
                "price": 25.99
              },
              "quantity": 2,
              "subtotal": 51.98
            }
          ],
          "orderDate": "2024-06-01T10:30:00.000+00:00",
          "totalAmount": 51.98,
          "createdAt": "2024-06-01T10:30:00.000+00:00",
          "updatedAt": "2024-06-01T10:30:00.000+00:00"
        }
        ```
*   **Search Orders by Customer Email (GET /api/orders/search?email=alice@example.com)**
*   **Filter Orders by Minimum Total (GET /api/orders?minTotal=50)**
*   **Filter Orders by Date Range (GET /api/orders?fromDate=2024-01-01&toDate=2024-12-31)**

## 📚 Project Structure Explanation
.
├── src/main/java/com/example/MiniOrderManagement/
│ ├── controller/ # REST API endpoints
│ ├── model/ # Data models (Customer, Product, Order, OrderItem)
│ ├── repository/ # Spring Data MongoDB interfaces for data access
│ ├── service/ # Business logic and coordination
│ └── MiniOrderManagementApplication.java # Main Spring Boot application entry point
├── src/main/resources/
│ └── application.properties # Application configuration (e.g., MongoDB URI)
├── pom.xml # Maven project configuration and dependencies
└── README.md # Project documentation (this file)

 ✅ Assumptions

*   MongoDB is running on `localhost:27017`.
*   Product price changes only affect newly created orders; existing orders retain the product's price at the time of their creation (due to embedded document design).
*   Customer email is treated as unique (`@Indexed(unique = true)`).
*   `totalAmount` and timestamps (`createdAt`, `updatedAt`, `orderDate`) are automatically handled by the service layer during order creation.

---
