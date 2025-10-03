
CREATE DATABASE IF NOT EXISTS payments_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE payments_db;


DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS agent;
DROP TABLE IF EXISTS department;

CREATE TABLE department (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(120) NOT NULL,
                            responsible_agent_id INT NULL
);

CREATE TABLE agent (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) UNIQUE,
                       password VARCHAR(200),
                       type VARCHAR(50) NOT NULL,
                       department_id INT NULL,
                       CONSTRAINT fk_agent_department FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE SET NULL
);


ALTER TABLE department
    ADD CONSTRAINT fk_department_responsible FOREIGN KEY (responsible_agent_id) REFERENCES agent(id) ON DELETE SET NULL;

CREATE TABLE payment (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         type VARCHAR(50) NOT NULL,
                         amount DECIMAL(15,2) NOT NULL,
                         date_payment DATE NOT NULL,
                         motif VARCHAR(255),
                         condition_validee BOOLEAN DEFAULT FALSE,
                         agent_id INT NOT NULL,
                         CONSTRAINT fk_payment_agent FOREIGN KEY (agent_id) REFERENCES agent(id) ON DELETE CASCADE
);
