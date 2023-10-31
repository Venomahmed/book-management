# Technical Task: Book Management System

Objective: Design and implement a robust RESTful API that manages bookEntities and their authorEntities.

## <u>Requirements:</u>
* Project Setup:
    * Use Maven for project management.
    * Define all necessary dependencies in the pom.xml file.
    * Ensure that the project can be built and packaged into a runnable JAR using Maven commands.
* Entities:
  * Author:
  * ID (Primary Key)
  * First Name
  * Last Name
  * Date of Birth
  * Biography (Text, max 1000 characters)
  * List of Books (One to Many relationship with Book)
  * Book:
  * ID (Primary Key)
  * Title
  * ISBN (Unique)
  * Publication Date
  * Summary (Text, max 500 characters)
  * Author (Many to One relationship with Author)
* API Endpoints:
  * Author:
  * CRUD operations
  * Retrieve bookEntities by an authorEntity
  * Book:
  * CRUD operations
  * Search bookEntities by title or authorEntity name
* Database:
    * Use an in-memory H2 database.
    * Write Liquibase changelogs for creating the necessary tables and relationships.
    * Implement database migrations using Liquibase integrated with Maven.
    * Use JPA for ORM.
* Error Handling:
    * Implement global exception handling.
    * Return detailed error responses with appropriate HTTP status codes.
* Validation:
    * Validate ISBN format.
    * Ensure that the authorEntity's date of birth is in the past.
    * Ensure that the bookEntity's publication date is not in the future.
    * Validate input lengths for biography and summary.
* Security:
    * Secure the API using JWT authentication.
        * Implement role-based access control:
        * USER Role: Can perform CRUD operations on both Author and Book entities.
        * ADMIN Role: Can execute all other queries, including search and any advanced operations added in the future.

* Testing:
    * Write unit tests for service layers using JUnit and Mockito.
    * Write integration tests for the API endpoints.
* Documentation:
    * Use Swagger to document the API endpoints.


## <u>Acceptance Criteria:</u>
* Project Setup:
    * The project follows the standard Maven structure.
    * The pom.xml correctly lists all dependencies.
    * The project builds and packages into a runnable JAR using mvn clean package.
* Entities:
    * Both Author and Book entities are defined with the specified attributes.
    * JPA annotations are correctly used for primary keys, relationships, and constraints.
* API Endpoints:
    * CRUD operations for both Author and Book are implemented and exposed.
    * The endpoint to retrieve bookEntities by an authorEntity is functional.
    * The search endpoint filters bookEntities based on title or authorEntity name.
* Database:
    * The H2 database is correctly integrated and functional.
    * Liquibase changelogs are present and correctly define the necessary tables and relationships.
    * Database migrations are set up using Liquibase and can be executed via Maven.
    * JPA is used for ORM and interacts seamlessly with the database structure created by Liquibase.
* Error Handling:
    * Global exception handling is in place.
    * Specific exceptions return detailed error responses with the correct HTTP status codes.
* Validation:
    * ISBNs are validated for the correct format.
    * Author's date of birth is validated to be in the past.
    * Book's publication date is validated to not be in the future.
    * Input lengths for biography and summary are validated.
* Security:
    * JWT authentication is correctly implemented and functional.
    * Users with the USER role can perform CRUD operations on both Author and Book entities.
    * Users with the ADMIN role can execute all other queries, including search and any advanced operations.
* Performance and Scalability (ASK DURING INTERVIEW) :
    * Pagination is implemented for list endpoints.
    * Database queries are optimized and do not show signs of inefficiencies.
* Testing:
    * Unit tests are present for service layers.
    * Integration tests are present for API endpoints.
    * The project achieves at least 80% code coverage (ASK DURING INTERVIEW).
* Documentation:
    * Swagger provides documentation for all API endpoints.

