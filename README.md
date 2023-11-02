# Book Management Assignment

## Table of Contents

- [Project Setup](#project-setup)
- [Entities](#entities)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Error Handling](#error-handling)
- [Validation](#validation)
- [Security](#security)
- [Performance and Scalability](#performance-and-scalability)
- [Testing](#testing)
- [Documentation](#documentation)

## Project Setup

- The project follows the standard Maven structure.
- The `pom.xml` correctly lists all dependencies.
- The project builds and packages into a runnable JAR using `mvn clean package`.

## Entities

- Both Author and Book entities are defined with the specified attributes.
- JPA annotations are correctly used for primary keys, relationships, and constraints.

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

- Global exception handling is in place.
- Specific exceptions return detailed error responses with the correct HTTP status codes.

## Validation

* NOTE: None of the validations are implemented due to **lack of time**.
  - ISBNs are validated for the correct format.
  - Author's date of birth is validated to be in the past.
  - Book's publication date is validated to not be in the future.
  - Input lengths for biography and summary are validated.

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

## Documentation

- Swagger provides documentation for all API endpoints.