# 💼 Job Portal System

## 📌 Project Overview

The Job Portal System is a Java-based application that connects job seekers with employers. It allows candidates to search and apply for jobs, while employers can post, manage, and track job applications.

This project is developed using Core Java, JDBC, and MySQL.

## 🚀 Features

### 👤 Candidate
- Register and log in
- View available jobs
- Search and filter jobs
- Apply for jobs
- View applied jobs and application status

### 🏢 Employer
- Register and log in
- Post new job openings
- View posted jobs
- Edit and delete job postings
- View candidate applications
- Update application status:
  - Applied
  - Shortlisted
  - Rejected

## 🛠️ Technologies Used

- Java
- JDBC
- MySQL
- MySQL Workbench
- Visual Studio Code

## 📂 Project Structure

```text
JobPortalSystem_Full_Source/
│
├── src/
│   ├── DBConnection.java
│   ├── User.java
│   ├── Job.java
│   ├── AuthService.java
│   ├── JobService.java
│   ├── ApplicationService.java
│   └── Main.java
│
├── lib/
│   └── mysql-connector-j-26.7.0.jar
│
├── database.sql
├── README.md
└── .gitignore
⚙️ Setup and Installation
1. Clone the Repository
git clone https://github.com/ramashanmugi-v/Job-Portal-System.git
2. Open the Project

Open the project folder in Visual Studio Code.

3. Create the Database
Open MySQL Workbench.
Run the SQL script from database.sql.
Make sure the JobPortalDB database and its tables are created.
4. Configure Database Connection

Open src/DBConnection.java and update the MySQL username and password according to your local setup.

5. Compile the Project

Run the following command from the project folder:

javac -cp "lib\mysql-connector-j-26.7.0.jar" -d out src\*.java
6. Run the Application
java -cp "out;lib\mysql-connector-j-26.7.0.jar" Main
🔄 Application Workflow
Register as a Candidate or Employer.
Log in using your registered account.
Employers can post and manage job openings.
Candidates can search for jobs and apply.
Employers can review applications and update their status.
🎯 Learning Outcomes
Understanding Core Java concepts
Implementing JDBC connectivity
Performing CRUD operations using MySQL
Working with role-based access
Managing job applications and statuses
Building a console-based Java application
🎥 Project Demo Video

https://drive.google.com/drive/folders/12qh6rxbp2cBfh2p3eBrTTQLTNM_8iV_0?usp=drive_link
🔮 Future Enhancements
Graphical User Interface using Java Swing
Password hashing and improved security
Email notifications
Advanced job search and filtering
Admin dashboard
👩‍💻 Developed By

V. Ramashanmugi

📄 License

This project was developed for educational and internship purposes.
