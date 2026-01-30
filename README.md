# CRM Application

This is a backend **CRM application** built with **JHipster 8.11.0** and Spring Boot.  
The project provides core CRM functionality such as user management, courses, and learning materials, and also includes **custom-developed backend logic** implemented manually.

---

## 🚀 Technologies

- Java 17
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA
- Liquibase (Database Migrations)
- MySQL
- Maven
- Docker (optional)

---

## 📦 Project Overview

The CRM application is designed to manage:

- Users and roles
- Courses
- Course materials (PDFs)
- User enrollments in courses

JHipster was used to generate the base project structure and security setup, while **business logic and custom APIs were extended manually** to fit real-world CRM requirements.

---

## 🧩 Main Modules

### 👤 User & Authority

- User authentication and authorization using JWT
- Role-based access control

### 📘 Course

- Course management
- Course metadata and configuration

### 📄 CoursePdf

- Course learning materials
- PDF file management related to courses

### 🔗 Enrollment

- Connects users with courses
- Tracks enrollment status and progress
- Prevents duplicate enrollments
- Automatically completes courses based on progress

---

## ✨ Custom Backend Features

- Custom service-layer business logic
- Custom REST endpoints beyond standard CRUD
- Enrollment validation and progress tracking
- Liquibase-based database schema management
- Clean separation of domain, service, and controller layers

---

## 🔐 Security

All REST APIs are secured using **JWT authentication** provided by Spring Security.

Login endpoint:

```http
POST /api/authenticate
```
