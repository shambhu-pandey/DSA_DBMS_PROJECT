
# 🚑 Ambulance Dispatch System – MCA Final Project

A real-time web application designed to manage emergency ambulance dispatches efficiently. Built for academic demonstration with robust backend logic, ETA prediction, notification handling, and auto-reassignment features.

---

## 📌 Project Overview

This system allows hospitals and dispatchers to:
- Receive emergency requests from patients
- Assign ambulances based on availability and proximity
- Predict ETA using location and traffic data
- Notify stakeholders via backend alerts
- Auto-reassign ambulances if delays occur

---

## 🛠️ Technologies Used

| Layer        | Technology            |
|--------------|------------------------|
| Frontend     | HTML, CSS, JavaScript |
| Backend      | Java, Spring Boot     |
| Database     | MongoDB               |
| Styling      | Bootstrap             |
| API Layer    | RESTful Services      |
| Tools        | MongoDB Compass, VS Code |

---

## 🗂️ Project Structure

```
ambulance-dispatch/
├── backend/
│   ├── controllers/
│   ├── models/
│   ├── repositories/
│   └── services/
├── frontend/
│   ├── index.html
│   ├── style.css
│   └── script.js
├── database/
│   └── MongoDB Collections:
│       ├── emergency_requests
│       ├── ambulances
│       ├── hospitals
│       ├── dispatch_history
│       └── notifications
```

---

## ⚙️ Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/ambulance-dispatch.git
```

### 2. Start MongoDB Server
```bash
mongod
```

### 3. Import Sample Data (Optional)
Use MongoDB Compass or shell:
```javascript
use ambulance_db
db.emergency_requests.insertOne({ ... })
```

### 4. Run Spring Boot Backend
```bash
cd backend
./mvnw spring-boot:run
```

### 5. Open Frontend
Open `index.html` in browser or deploy via Apache/Nginx.

---

## 📊 MongoDB Query Bank

Refer to [`queries.md`](./queries.md) for all MongoDB queries from Level 0 to Level 5.

---

## 🚨 Key Features

- ✅ Real-time ambulance assignment
- ✅ ETA prediction and sorting
- ✅ Auto-reassignment logic
- ✅ Modular frontend with sticky navbar
- ✅ Notification system with backend triggers
- ✅ Fair contribution tracking for team members

---

## 👥 Team Members

- **Shambhu Pandey** – Backend integration, MongoDB queries, UI modularization, viva preparation
- **Priyanshu Adhikari** – Frontend design, API testing, documentation, presentation support


---

## 📬 Contact

For questions or feedback:
- 📧 Email: shambhupandey2025@vitstudent.ac.in
- 🏫 VIT Chennai – MCA Final Year

---

*This project is built for academic purposes and showcases real-world emergency dispatch logic using modern web technologies.*
```

---

