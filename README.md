# Concurrent CSV Processing System

## Project Overview

The **Concurrent CSV Processing System** is a Spring Boot backend application designed to process CSV files efficiently using multithreading.

The application supports:

- Uploading CSV files
- Processing records concurrently
- Storing processed data in a PostgreSQL database
- Exposing APIs to monitor processing status and statistics

The system demonstrates how to build **high-performance backend services using concurrency and asynchronous processing**.

---

# Application Architecture


The application follows a **layered architecture**, which separates the system into different components based on their responsibilities. This design improves maintainability, scalability, and clarity of the codebase.

The **Controller layer** is responsible for handling incoming HTTP requests and exposing REST APIs to clients. It receives CSV files, triggers processing, and returns responses such as processing status and statistics.

The **Service layer** contains the core business logic of the application. It manages CSV parsing, concurrent processing of records, and coordination between different components. This layer also implements the concurrency model used for processing files and records in parallel.

The **Repository layer** interacts with the database using Spring Data JPA. It handles persistence operations such as saving processed records and storing file processing statuses.

Finally, the **Database layer** uses PostgreSQL to store processed CSV records and track the status of uploaded files. This ensures that processed data can be retrieved and monitored through the provided APIs.


---

## Controller Layer

Handles incoming HTTP requests.

Main controller:

### API Endpoints
POST /api/v1/csv/upload
GET /api/v1/csv/files/status
GET /api/v1/csv/stats


---

# Concurrency Model

The system implements **multi-level concurrency** to improve performance.

## 1. File-Level Concurrency

Each uploaded CSV file is processed by a **separate worker thread**.

## 2. Record-Level Concurrency

Each record inside the CSV file is processed **independently and concurrently**.

Each processed record is then stored in the database.

### Benefits

- Faster processing of large CSV files
- Efficient CPU utilization
- Scalable processing architecture
- Reduced total processing time

---

# Instructions to Run the Application

- 1. Clone the Repository
- 2. Navigate into the Project
- 3. Configure Environment Variables
- 4. Configure Database
- 5. Build and Run the Application
