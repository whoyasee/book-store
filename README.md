# Book Store API
A simple imitation of the backend of online book store application, 
using Spring Boot 

## Technologies 


- **Spring Boot**: A powerful framework for building Java-based applications.
- **Spring Security**: Ensures secure user authentication and authorization.
- **Spring Data JPA**: Simplifies database operations through the JPA specification.
- **Maven**: An open-source build automation and project management tool widely used for Java applications
- **PostgreSQL**: Relational database management system
- **Flyway**: An open-source database migration tool that helps you manage schema changes in a safe and repeatable way.
- **Docker**: A platform designed to help developers build, share, and run container applications
- **Testcontainers**: A library that helps you run module-specific Docker containers to simplify Integration Testing


## API Functionalities


- Registration of users;
- Authentication;
- Authorization
    - Unregistered Users
        - Can register (`POST /register`).
    - Users with "USER" Role
        - Can retrieve information about book by id and get book comments, access the list of books(`GET /books/{id}`, `GET /books`, `GET /books/{id}/comments`).
        - Can retrieve information about author by id and access the list of authors(`GET /authors/{id}`, `GET /authors`).
        - Can view their own orders, shopping cart or wish list (`GET /orders`, `GET /shopping-carts/by-user`, `GET /wish-list/by-user`).
        - Can confirm orders (`POST /orders/confirm`).
        - Can add books to their shopping cart or wish list (`PUT /shopping-carts/by-user`, `PUT /wish-list/by-user`).
        - Can remove book from shopping cart or wish list (`DELETE /shopping-carts/books`, `DELETE /wish-list/books`).
        - Can clear shopping cart or wish list (`DELETE /shopping-carts/books/clear`, `DELETE /wish-list/books/clear`)
    - Users with "ADMIN" Role
        - Can retrieve information about user by email and access the list of users(`GET /users/by-email`, `GET /users`).
        - Can retrieve information about book by id and get book comments, access the list of books(`GET /books/{id}`, `GET /books`, `GET /books/{id}/comments`).
        - Can retrieve information about author by id and access the list of authors(`GET /authors/{id}`, `GET /authors`).
        - Can add books, authors and post comments (`POST /books`, `POST /authors`, `POST /books/{id}/comments`).
        - Can assign book to author (`PUT /authors/{authorId}/books/{bookId}`).
        - Can delete books, authors and comments by id (`DELETE /books/{id}`, `DELETE /authors/{authorId}`, `DELETE /books/{id}/comments/{commentId}`).

## Setup and Usage

To set up and use the Book Store API, follow these steps:

1. Clone the repository to your local machine.
2. Configure the database settings in the application properties.
3. Build and run the application using your Java IDE or build tool.




