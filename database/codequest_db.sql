CREATE DATABASE IF NOT EXISTS codequest_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE codequest_db;

CREATE TABLE IF NOT EXISTS users (
 id INT PRIMARY KEY AUTO_INCREMENT,
 username VARCHAR(50) NOT NULL UNIQUE,
 email VARCHAR(100) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 total_xp INT NOT NULL DEFAULT 0,
 player_level INT NOT NULL DEFAULT 1,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS admins (
 id INT PRIMARY KEY AUTO_INCREMENT,
 username VARCHAR(50) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS missions (
 id INT PRIMARY KEY AUTO_INCREMENT,
 title VARCHAR(100) NOT NULL,
 description TEXT NOT NULL,
 category VARCHAR(30) NOT NULL,
 difficulty VARCHAR(20) NOT NULL,
 option_a VARCHAR(255) NOT NULL,
 option_b VARCHAR(255) NOT NULL,
 option_c VARCHAR(255) NOT NULL,
 option_d VARCHAR(255) NOT NULL,
 correct_answer VARCHAR(255) NOT NULL,
 xp_reward INT NOT NULL DEFAULT 50,
 created_by INT NOT NULL,
 CONSTRAINT fk_mission_admin FOREIGN KEY(created_by) REFERENCES admins(id)
);
CREATE TABLE IF NOT EXISTS mission_submissions (
 id INT PRIMARY KEY AUTO_INCREMENT,
 user_id INT NOT NULL,
 mission_id INT NOT NULL,
 answer VARCHAR(255) NOT NULL,
 is_correct BOOLEAN NOT NULL DEFAULT FALSE,
 completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_submission_user FOREIGN KEY(user_id) REFERENCES users(id),
 CONSTRAINT fk_submission_mission FOREIGN KEY(mission_id) REFERENCES missions(id)
);

-- Demo admin: username admin, password Admin@123
-- Change this password after first login. Demo only; replace with a securely hashed password in production.
INSERT IGNORE INTO admins(username,password) VALUES('admin','Admin@123');

INSERT INTO missions(title,description,category,difficulty,option_a,option_b,option_c,option_d,correct_answer,xp_reward,created_by)
SELECT 'Java output basics','Which keyword is used to define a class in Java?','Java','Easy','class','define','struct','object','class',50,id FROM admins WHERE username='admin'
AND NOT EXISTS (SELECT 1 FROM missions WHERE title='Java output basics');
INSERT INTO missions(title,description,category,difficulty,option_a,option_b,option_c,option_d,correct_answer,xp_reward,created_by)
SELECT 'SQL query basics','Which SQL command reads data from a table?','SQL','Easy','GET','SELECT','OPEN','READ','SELECT',50,id FROM admins WHERE username='admin'
AND NOT EXISTS (SELECT 1 FROM missions WHERE title='SQL query basics');
