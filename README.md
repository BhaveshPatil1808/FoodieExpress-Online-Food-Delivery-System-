# 🍔 FoodieExpress

FoodieExpress is a full‑stack food delivery platform built with **Spring Boot** and **Thymeleaf**, designed to simulate a real‑world multi‑role system. It provides separate flows for **customers, restaurants, and admins**, with secure authentication, dynamic dashboards, and a modern glassmorphism‑inspired UI.

---

## ✨ Features

- **Multi‑Role Architecture**
  - 👤 Customer: Register, log in, browse menus, place orders, track deliveries
  - 🍴 Restaurant: Manage menus, view incoming orders, update order status
  - 🛠️ Admin: Oversee users, restaurants, and orders with analytics dashboard

- **Authentication & Security**
  - Role‑based access control with **Spring Security**
  - Password encryption with **BCrypt**
  - Session management and protected routes

- **UI/UX**
  - Built with **Thymeleaf** templates and **Bootstrap 5**
  - **Liquid glass (glassmorphism)** login and dashboard design
  - Responsive layouts for desktop and mobile

- **Architecture**
  - Currently a **modular monolith** with clear separation of concerns
  - Designed to be **microservices‑ready** (customer‑service, order‑service, restaurant‑service, etc.)
  - REST endpoints for modularity and future scaling

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot, Spring Data JPA, Spring Security  
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome  
- **Database**: MySQL / PostgreSQL  
- **Build Tool**: Maven  
- **Other**: Hibernate ORM, Lombok  

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3+
- MySQL/PostgreSQL running locally

### Installation
```bash
# Clone the repository
git clone https://github.com/your-username/foodieexpress.git
cd foodieexpress

# Build the project
mvn clean install


Access
Customer login: http://localhost:8080/customer/login

Restaurant login: http://localhost:8080/restaurant/login

Admin login: http://localhost:8080/admin/login


-----------------------------------------------------------------------------------------------------------------------------------------------------------------
📸 Screenshots
<img width="1896" height="935" alt="image" src="https://github.com/user-attachments/assets/0b8dab96-5f95-4265-917c-f764435959de" />

<br></br>

<img width="1898" height="852" alt="image" src="https://github.com/user-attachments/assets/b2c12b01-15c7-404e-a368-a3a354b96542" />
<br></br>

# Run the application
mvn spring-boot:run







🔮 Future Enhancements
Split into Spring Boot microservices with Eureka, Gateway, Config Server

Add JWT‑based authentication for stateless APIs

Integrate payment gateway simulation

Add delivery partner role with live tracking
