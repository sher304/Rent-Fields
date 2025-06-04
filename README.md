## 📄 API Documentation

This project includes full Swagger (OpenAPI 3.0) documentation.

- Access the Swagger UI here: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Main Endpoints

| Method | Endpoint                          | Description                                     |
|--------|-----------------------------------|-------------------------------------------------|
| POST   | `/api/v1/registration`            | Register a new user                             |
| POST   | `/api/v1/login`                   | Log in                                          |
| POST   | `/api/v1/field`                   | Create a new field (Admin only)                 |
| GET    | `/api/v1/fields`                  | View all fields                                 |
| GET    | `/api/v1/fields/search`           | Search fields by location/title/description     |
| GET    | `/api/v1/fields/time`             | Filter fields by time availability              |
| POST   | `/api/v1/reservation`             | Make a reservation                              |
| DELETE | `/api/v1/reservation/cancel/{id}` | Cancel a reservation                            |
| POST   | `/api/v1/payment`                 | Make a payment                                  |
| POST   | `/api/v1/rate`                    | Add a field review                              |
| GET    | `/api/v1/me`                      | Get logged-in user's profile                    |
| GET    | `/api/v1/manage`                  | Manager's fields                                |
| GET    | `/api/v1/manage/bookingsAndReservations` | Manager's reservations/bookings         |
| GET    | `/api/v1/users`                   | Admin: Get all users                            |
| DELETE | `/api/v1/user/{id}`               | Admin: Delete a user                            |
