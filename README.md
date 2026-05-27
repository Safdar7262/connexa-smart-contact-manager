#  Smart Contact Manager (SCM 2.0)

A full-stack Contact Management System built using **Spring Boot**, designed to manage personal and professional contacts efficiently.  
This project demonstrates real-world implementation of authentication, cloud storage, and modern backend architecture.

---

##  Project Overview

Smart Contact Manager (SCM 2.0) is a secure and scalable web application where users can:

- Manage contacts (Add, Update, Delete, View)
- Upload profile images using Cloudinary
- Login using Google & GitHub (OAuth2)
- Experience secure authentication with Spring Security
- Work with a responsive UI using Thymeleaf

This project follows **layered architecture**:


---

##  Key Features

###  Authentication & Security
- Spring Security integration
- OAuth2 Login:
  - Google Login
  - GitHub Login
- Secure user session handling

---

###  Contact Management
- Add new contact
- Update existing contact
- Delete contact
- View all contacts
- Sort contacts (Ascending / Descending)

---

###  Cloud Integration
- Image upload using **Cloudinary**
- Store and retrieve profile images

---

###  Validation
- Form validation using `@Valid`, `@NotBlank`, `@Size`
- User-friendly error messages

---

###  Monitoring
- Spring Boot Actuator integration for application monitoring

---

##  Tech Stack

### 🔹 Backend
- Java 21
- Spring Boot 3
- Spring MVC
- Spring Data JPA (Hibernate)

###  Security
- Spring Security
- OAuth2 Client (Google & GitHub)

###  Frontend
- Thymeleaf
- HTML, CSS, JavaScript

### Database
- MySQL

###  Cloud
- Cloudinary (Image Storage)

###  Build Tool
- Maven

---

##  Project Structure

com.scm
├── controllers → Handle HTTP requests
├── services → Business logic
├── repositories → Database layer (JPA)
├── entities → Database tables
├── forms → Validation classes
├── helpers → Utility classes
├── config → Security & OAuth configuration


###  OAuth2 Configuration

- Google OAuth Login
- GitHub OAuth Login

###  Cloudinary Configuration

Used for storing contact profile images.


###  Server Configuration

- Port: `8081`


##  How to Run the Project

### 1️⃣ Clone Repository

git clone https://github.com/Shahbaz786929/smart-contact-manager.git

### 2️⃣ Open in IDE
- VS Code / IntelliJ IDEA

### 3️⃣ Configure MySQL Database
- Create database: `scm20`
- Update credentials in `application.properties`

### 4️⃣ Run Application
Run the main class:
ScmApplication.java

### 5️⃣ Access Application
http://localhost:8081


##  Author

**Mohammad Shahbaz**
**shahbaz786929@gmail.com**
