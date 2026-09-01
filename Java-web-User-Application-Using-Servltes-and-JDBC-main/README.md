# Srinath User Portal

A Java 17 web application built with Jakarta Servlets, Maven and JDBC/MySQL.

## Features
- Register a user
- Sign in with credentials
- View registered users
- Remove a user
- Update a password

## Local setup
Create the database and tables in MySQL:

```sql
CREATE DATABASE srinath_user_portal;
USE srinath_user_portal;

CREATE TABLE users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL
);

CREATE TABLE credentials (
  credentials_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  username VARCHAR(50) NOT NULL UNIQUE,
  login_password VARCHAR(100) NOT NULL,
  CONSTRAINT fk_credentials_user
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE
);
```

The application is configured for MySQL on `localhost:3306` with user `root`.

## Maven
```bash
mvn clean package
```

Deploy the generated WAR to a Jakarta Servlet 6 compatible server such as Tomcat 10.1+.
