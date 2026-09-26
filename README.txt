JOB PORTAL SYSTEM (Core Java + JDBC + MySQL)
=============================================

Features covered:
- Candidate and Employer registration/login/logout, role-specific menus
- Employer: post, view, edit, delete own jobs
- Candidate: browse, keyword search, location/job-type filters, apply, view applications
- Employer: view applications for own jobs and update status (Applied/Shortlisted/Rejected)
- Duplicate applications prevented by database unique constraint

SETUP
1. In MySQL Workbench, run database.sql.
2. Open src/DBConnection.java and replace YOUR_MYSQL_PASSWORD with your MySQL root password.
3. Place mysql-connector-j-26.7.0.jar in the project's lib folder.
4. From the project folder in PowerShell run:
   javac -cp "lib\mysql-connector-j-26.7.0.jar" -d out src\*.java
   java -cp "out;lib\mysql-connector-j-26.7.0.jar" Main

NOTE
This is a learning/demo console application. Passwords are stored as plain text for simplicity.
For real deployment, use a secure password-hashing approach, validation, and stronger configuration.
Optional Admin feature is not included because the problem statement marks it optional.
