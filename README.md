# Book Management Assignment

## Table of Contents

- [JDK](#jdk)
- [Swagger Details](#swagger-details)
- [User Details](#user-details)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Error Handling](#error-handling)
- [Validation](#validation)
- [Security](#security)
- [Performance and Scalability](#performance-and-scalability)
- [Testing](#testing)
- [Documentation](#documentation)

## JDK

- openjdk version "17.0.9" 2023-10-17 LTS
- OpenJDK Runtime Environment Corretto-17.0.9.8.1 (build 17.0.9+8-LTS)
- OpenJDK 64-Bit Server VM Corretto-17.0.9.8.1 (build 17.0.9+8-LTS, mixed mode, sharing)

## Swagger Details

- URL: http://localhost:8080/swagger-ui/index.html#/

## User Details

- istiak/password
- user1/pwd1
- user2/pwd2
- user3/pwd3

Ingested manually using UserDetailsServiceImpl as hardcode object due to lack of time.

## API Endpoints

- CRUD operations for both Author and Book are implemented and exposed.
- The endpoint to retrieve books by an author is functional.
- The search endpoint filters books based on title or author name.

## Database

- The H2 database is correctly integrated and functional.
- Liquibase changelogs are present and correctly define the necessary tables and relationships.
- Database migrations are set up using Liquibase and can be executed via Maven.
- JPA is used for ORM and interacts seamlessly with the database structure created by Liquibase.

## Error Handling

- Controller Advice for exception handling.

## Validation

* NOTE: Few of the validations are implemented due to **lack of time**.

## Security

- JWT authentication is correctly implemented and functional.
- Users with the USER role can perform CRUD operations on both Author and Book entities.
- Users with the ADMIN role can execute all other queries, including search and any advanced operations.

## Performance and Scalability

- Server side pagination implemented for /v1/books/filter
- Database queries are optimized and do not show signs of inefficiencies.

## Testing

- Unit tests are present for service layers.
- Integration tests are present for API endpoints.
- The project achieves at least 80% code coverage.
