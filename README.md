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




Access
Customer login: http://localhost:8080/customer/login

Restaurant login: http://localhost:8080/restaurant/login

Admin login: http://localhost:8080/admin/login


-----------------------------------------------------------------------------------------------------------------------------------------------------------------
📸 Screenshots
<img width="1896" height="935" alt="image" src="https://github.com/user-attachments/assets/0b8dab96-5f95-4265-917c-f764435959de" />

<br></br>
<h1>Restaurant Module</h1>
<br></br>
<img width="1898" height="852" alt="image" src="https://github.com/user-attachments/assets/b2c12b01-15c7-404e-a368-a3a354b96542" />
<br></br>
<img width="1920" height="897" alt="image" src="https://github.com/user-attachments/assets/a68b9b23-37de-4bc6-be4a-4e51e3b6811d" />
<br></br>

<img width="1901" height="943" alt="image" src="https://github.com/user-attachments/assets/b28b9473-76be-434a-99b0-9cd409ea1fa3" />
<br></br>
<img width="1897" height="942" alt="image" src="https://github.com/user-attachments/assets/d437f942-4a1e-4970-8488-518d1c601f1f" />
<br></br>
<img width="1897" height="932" alt="image" src="https://github.com/user-attachments/assets/dae5d9ae-b00b-4cf2-9ad8-98eaf406530e" />
<br></br>
<img width="1898" height="233" alt="image" src="https://github.com/user-attachments/assets/b5a121f4-a3d7-40cc-adbd-fe7989316d15" />





<h1>Customer Module</h1>
<br></br>
<img width="1920" height="934" alt="image" src="https://github.com/user-attachments/assets/538847cc-687e-4da4-818c-3d7291312bec" />
<br></br>
<img width="1920" height="789" alt="image" src="https://github.com/user-attachments/assets/b0747222-cbb6-46a9-9948-299150126981" />
<br></br>
<img width="1899" height="938" alt="image" src="https://github.com/user-attachments/assets/b9e0f481-5dc4-4531-a427-0b9440bccb4d" />
<br></br>
<img width="1901" height="942" alt="image" src="https://github.com/user-attachments/assets/338d4ca3-2d75-4f99-8db7-fc6798eab88c" />
<br></br>
<h1>Most IMP Payment Gateway </h1>
<img width="1209" height="796" alt="image" src="https://github.com/user-attachments/assets/de51de6f-2b47-4823-9914-502f2bccdb63" />
<br></br>
<img width="718" height="617" alt="image" src="https://github.com/user-attachments/assets/969ce8d1-291d-47ff-aaea-78a634aefa73" />

<br><br>
<img width="1890" height="905" alt="image" src="https://github.com/user-attachments/assets/95fa3f9e-b12b-4992-b1ae-f556289d1c6d" />

<br></br>
<img width="1890" height="905" alt="image" src="https://github.com/user-attachments/assets/b730fd80-948b-452b-abf2-8fe1f134369f" />





# Run the application
mvn spring-boot:run







🔮 Future Enhancements
Split into Spring Boot microservices with Eureka, Gateway, Config Server

Add JWT‑based authentication for stateless APIs

Integrate payment gateway simulation

Add delivery partner role with live tracking
