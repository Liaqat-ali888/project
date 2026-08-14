-- ===================================================
--  STUDENT TRACKER DATABASE SCHEMA
--  Database Name: student_db (CHANGED)
--  User: root
--  Password: (empty or your password)
-- ===================================================

DROP DATABASE IF EXISTS student_db;
CREATE DATABASE student_db;
USE student_db;

-- ========== STUDENTS TABLE ==========
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- ========== SUBJECTS TABLE ==========
CREATE TABLE subjects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- ========== TOPICS TABLE ==========
CREATE TABLE topics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT NOT NULL,
    name VARCHAR(150) NOT NULL,
    completed BOOLEAN DEFAULT FALSE,
    confidence_level INT DEFAULT 1,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- ========== DEADLINES TABLE ==========
CREATE TABLE deadlines (
    id INT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    date VARCHAR(20),
    subject_name VARCHAR(100),
    type VARCHAR(50),
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- ========== INSERT SAMPLE DATA ==========

INSERT INTO students (name) VALUES ('Ali Hassan');
INSERT INTO students (name) VALUES ('Sara Khan');
INSERT INTO students (name) VALUES ('Ahmed Ali');

-- Subjects
INSERT INTO subjects (student_id, name) VALUES (1, 'Object Oriented Programming');
INSERT INTO subjects (student_id, name) VALUES (1, 'Data Structures');
INSERT INTO subjects (student_id, name) VALUES (2, 'Database Systems');
INSERT INTO subjects (student_id, name) VALUES (2, 'Web Development');

-- Topics for OOP
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (1, 'Classes and Objects', TRUE, 4);
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (1, 'Inheritance', TRUE, 3);
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (1, 'Polymorphism', FALSE, 2);

-- Topics for Data Structures
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (2, 'Arrays', TRUE, 5);
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (2, 'Linked Lists', TRUE, 4);
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (2, 'Stacks', FALSE, 2);

-- Topics for Database Systems
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (3, 'Normalization', TRUE, 4);
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (3, 'SQL Queries', TRUE, 5);

-- Topics for Web Development
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (4, 'HTML5', TRUE, 5);
INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (4, 'CSS3', FALSE, 3);

-- Deadlines
INSERT INTO deadlines (subject_id, title, date, subject_name, type) VALUES (1, 'OOP Assignment 1', '2025-05-15', 'OOP', 'Assignment');
INSERT INTO deadlines (subject_id, title, date, subject_name, type) VALUES (1, 'Mid Exam', '2025-05-25', 'OOP', 'Exam');
INSERT INTO deadlines (subject_id, title, date, subject_name, type) VALUES (2, 'DS Lab', '2025-05-20', 'Data Structures', 'Assignment');
INSERT INTO deadlines (subject_id, title, date, subject_name, type) VALUES (3, 'Database Project', '2025-06-01', 'Database Systems', 'Assignment');
INSERT INTO deadlines (subject_id, title, date, subject_name, type) VALUES (4, 'Web Project', '2025-05-30', 'Web Development', 'Assignment');

-- ===================================================
--  SETUP COMPLETE
-- ===================================================
