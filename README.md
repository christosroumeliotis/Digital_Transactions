<h1>🏦 Digital Bank Backend API</h1>

A secure and scalable backend system that simulates core banking simple operations such as account management, transactions, and authentication. Built with Java Spring Boot.

<h2>🚀 Features</h2>

* Authentication & Security

      -User registration and login
      -JWT-based authentication
      -Password encryption using BCrypt

* Account Management

      -Create bank accounts for users
      -View account details and balances
      -Support for multiple accounts per user

* Transactions

      -Deposit funds
      -Withdraw funds
      -Transfer between accounts
      -Transaction request limit per IP

* System Features 

      -Global exception handling
      -Caching
      -DTO-based API design
      -Input validation
      -Layered architecture (Controller → Service → Repository)

<h2>Tech Stack</h2>

* Java 21
* Spring Boot
* Spring Security (JWT)
* Spring Data JPA
* MySQL
* Hibernate
* Maven
* Lombok
* Docker

<h2>⚙️ How to Run</h2>
1. Clone repository
   git clone https://github.com/christosroumeliotis/Digital_Transactions
2. Run docker-compose up --build
4. Access API http://localhost:8080

<h2>📌 API Overview</h2>
http://localhost:8080/swagger-ui/index.html
http://16.171.0.141:8080/swagger-ui/index.html
