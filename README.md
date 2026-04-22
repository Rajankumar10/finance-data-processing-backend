# Data Processing and Access Control Backend

This project is my submission for the Zorvyn Backend Developer Intern assignment.

I built it as a simple Spring Boot backend for a finance dashboard system where different users can access data based on their role. The main goal of this project was to keep the code clean, easy to understand, and logically structured.

## What This Project Does

- manages users with different roles
- stores financial records like income and expense entries
- provides dashboard summary data
- restricts actions based on user role
- validates input and returns proper error responses

## Roles Used

- `ADMIN`
  Can manage users and financial records
- `ANALYST`
  Can view financial records and dashboard data
- `VIEWER`
  Can only view dashboard data

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- JUnit and MockMvc for testing

## Project Structure

```text
src/main/java/com/zorvyn/financebackend
├── config
├── constants
├── controller
│   ├── api
│   └── impl
├── dto
├── enums
├── exception
├── mapper
├── model
├── repository
├── security
├── service
│   ├── api
│   └── impl
├── util
└── validator
```

I used separate `api` and `impl` folders in controller and service to keep the structure clear and organized.

## Main Features

### 1. User Management

- create users
- update users
- assign roles
- manage active and inactive status

### 2. Financial Record Management

- create financial records
- view all records
- view single record
- update records
- delete records
- filter records by type, category, and date range

### 3. Dashboard Summary

The dashboard summary API returns:

- total income
- total expense
- net balance
- category wise totals
- recent activity
- monthly trends

### 4. Access Control

Access is controlled at backend level using a simple request header:

```text
X-User-Id
```

This was kept simple on purpose so the focus stays on backend logic and access control instead of full authentication setup.

## API List

- `GET /health`
- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PATCH /api/users/{id}`
- `GET /api/records`
- `GET /api/records/{id}`
- `POST /api/records`
- `PATCH /api/records/{id}`
- `DELETE /api/records/{id}`
- `GET /api/dashboard/summary`

## Demo Users

If the database is empty, the project seeds these users:

- `1` = Rajan Kumar (`ADMIN`)
- `2` = Deepak Kumar (`ANALYST`)
- `3` = Sinjon Saha (`VIEWER`)

## How To Run

```bash
mvn -Dmaven.repo.local=.m2 spring-boot:run
```

## How To Run Tests

```bash
mvn -Dmaven.repo.local=.m2 test
```

## Database

This project uses H2 file-based database for simplicity.

H2 console:

```text
http://localhost:8080/h2-console
```

Use this JDBC URL:

```text
jdbc:h2:file:/Users/rajankumar/Desktop/project/data/finance-backend-db
```

Username:

```text
sa
```

Password:

Leave it empty.

## Example Request

Dashboard summary:

```bash
curl -H "X-User-Id: 3" http://localhost:8080/api/dashboard/summary
```

Create record as admin:

```bash
curl -X POST http://localhost:8080/api/records \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "amount": 5000,
    "type": "INCOME",
    "category": "Salary",
    "recordDate": "2026-04-05",
    "notes": "Monthly salary"
  }'
```

## Assumptions

- full authentication was not added because the assignment allowed mock authentication
- H2 was chosen to make local setup fast and easy
- pagination and soft delete were not added to keep the project simple

## Notes

I tried to keep this project simple enough to explain easily, while still covering the main backend concepts asked in the assignment:

- API design
- data modeling
- service layer logic
- access control
- validation
- persistence
