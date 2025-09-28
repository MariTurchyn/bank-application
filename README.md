# 🏦 OnlineBanking – Simple Online Banking System

A **Spring Boot web application** that simulates a modern online banking system.  
Built to demonstrate **web development, databases, REST APIs, and clean architecture**.  

This project is perfect for showcasing my skills in **Java, Spring Boot, SQL, MVC, and RESTful API design**.

---

## ✨ Features

- 👤 **User authentication** (register, login, logout) with validation  
- 💰 **Bank accounts** – create accounts (checking/savings) with different currencies  
- ➕ **Deposit & Withdraw** funds into accounts  
- 🔄 **Transfer money** between accounts (if user has multiple)  
- 📊 **Dashboard** with sidebar, KPIs (total balance, account count), and quick actions  
- 🎨 **Responsive UI** built with **Thymeleaf templates + custom CSS**  
- ⚡ **REST API layer** (`/api/auth/*`, `/api/accounts/*`) for future integration with mobile apps or front-end frameworks  
- 🗄️ **Database persistence** using **H2 embedded DB (file mode)**  

---

## 🛠️ Tech Stack

- **Language**: Java 17  
- **Framework**: Spring Boot 3.3  
- **Database**: H2 (file-based, persists between runs)  
- **View Engine**: Thymeleaf (server-side HTML templates)  
- **Build Tool**: Maven  
- **Libraries**:  
  - **Spring Boot Starter Web** – controllers, REST, MVC  
  - **Spring Boot Starter Validation** – form validation  
  - **Spring Security Crypto** – BCrypt password hashing  
  - **Spring Boot Starter JDBC / JPA** – database access  
  - **Thymeleaf** – server-side rendering  
  - **H2 Database** – embedded SQL DB  

---

## 🚀 How to Run

### 1. Clone the repo
```bash
git clone https://github.com/YOUR_USERNAME/onlinebanking.git
cd onlinebanking
```
### 2. Build & run with Maven
```bash
mvn spring-boot:run
```

### 3. Open the database console (H2)
The app uses an embedded **H2 SQL database** that persists data between runs.

Go to: http://localhost:8080/h2-console

Use the following settings:

**JDBC URL**: jdbc:h2:file:./bankdb

**User**: sa

**Password**: (leave empty)

Here you can run queries like:
```bash
SELECT * FROM ACCOUNTS;
```
### 4. Open the web app in the browser
Open the app in your browser:
   http://localhost:8080/

   - Register a new user on the landing page
   - Then log in with the same credentials
   - You’ll be redirected to the Dashboard automatically
   - 
Here you can:

- See your accounts and balances
- Deposit, withdraw, or transfer money
- Add new accounts

--- 

## 📂 Project Structure
```bash
src/main/java
 ├─ com.masha.onlinebanking
 │   ├─ controller/        # SiteController (MVC, Thymeleaf), REST controllers (Auth, Account)
 │   ├─ model/             # Entities (User, Account)
 │   ├─ repo/              # Repositories (UserRepo, AccountRepo)
 │   ├─ service/           # Business logic (AuthService, AccountService)
 │   └─ OnlineBankingApp   # Main entry point
src/main/resources
 ├─ templates/             # Thymeleaf HTML templates (login, dashboard)
 ├─ static/                # CSS files (style.css)
 └─ application.properties # Spring configuration

```
---
## 🔍 Why This Project Matters

This project demonstrates:

- 🖥️ Building a **full-stack web app** with Spring Boot  
- 🎨 Designing both **MVC (server-side HTML)** and **REST APIs** for flexibility  
- 🗄️ Using an **embedded SQL database (H2)** with persistence  
- 🔐 Secure password handling with **BCrypt hashing**  
- 📂 Clean separation of **controllers, services, repos, and views**  
- 📊 A responsive **dashboard UI** styled like a real banking system  

It’s a **mini real-world banking app** that shows both **backend skills** (Spring, REST, SQL) and **frontend skills** (Thymeleaf, CSS) in one project.

## Screenshots
---
<img width="939" height="829" alt="image" src="https://github.com/user-attachments/assets/1c61e632-a6cc-475d-98f1-ccf18a5e5182" />

---
<img width="945" height="846" alt="image" src="https://github.com/user-attachments/assets/36ff23e5-7795-45f4-8d75-5c6895646685" />

---
<img width="1899" height="855" alt="image" src="https://github.com/user-attachments/assets/c863497d-80dc-4abc-adc0-7adc5b888cf2" />

---

<img width="1889" height="781" alt="image" src="https://github.com/user-attachments/assets/c79e50e4-475c-4cb5-99a7-9b8685a0fcfd" />

---

<img width="1139" height="484" alt="image" src="https://github.com/user-attachments/assets/209f848b-0e0b-49d6-8804-a5e1bef2a975" />


