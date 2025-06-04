# Rent Football Field

A Java Spring Boot web application for managing football field reservations, users, payments, and reviews with role-based access (Admin, Manager, User).

## Features

- User registration & login
- Role-based access (Admin, Manager, User)
- Add/view/delete football fields (Admin)
- Reserve and book fields (User)
- Payments linked to reservations
- Field reviews and ratings
- Manager dashboard to view own fields and reservations
- REST API (optional) & web UI (Thymeleaf)

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- H2 Database (in-memory)
- Thymeleaf (for frontend)
- Swagger (API docs)

## How to Run

1. Clone the repo  
   ```bash
   git clone https://github.com/sher304/Rent-Fields
   ```
2. Open in IntelliJ / VS Code
3. Run `RentFieldApplication.java`
4. Access:
   - Web UI: [http://localhost:8080/api/v1/login](http://localhost:8080/api/v1/login)
   - Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

## Roles

- **Admin** – Manage users, fields  
- **Manager** – View/manage own fields and bookings  
- **User** – Book fields, make payments, add reviews
