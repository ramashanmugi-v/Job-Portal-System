CREATE DATABASE IF NOT EXISTS JobPortalDB;
USE JobPortalDB;

CREATE TABLE IF NOT EXISTS users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role ENUM('Candidate','Employer') NOT NULL
);

CREATE TABLE IF NOT EXISTS jobs (
  job_id INT PRIMARY KEY AUTO_INCREMENT,
  employer_id INT NOT NULL,
  job_title VARCHAR(150) NOT NULL,
  company_name VARCHAR(150) NOT NULL,
  location VARCHAR(120),
  salary DECIMAL(12,2),
  job_type VARCHAR(50),
  description TEXT,
  required_skills TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_jobs_employer FOREIGN KEY (employer_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS applications (
  application_id INT PRIMARY KEY AUTO_INCREMENT,
  job_id INT NOT NULL,
  candidate_id INT NOT NULL,
  status ENUM('Applied','Shortlisted','Rejected') NOT NULL DEFAULT 'Applied',
  applied_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY unique_candidate_job (job_id,candidate_id),
  CONSTRAINT fk_app_job FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE,
  CONSTRAINT fk_app_candidate FOREIGN KEY (candidate_id) REFERENCES users(user_id) ON DELETE CASCADE
);
