CREATE DATABASE IF NOT EXISTS carshop;
USE carshop;
CREATE TABLE IF NOT EXISTS purchaser
(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	lastname CHAR(100) NOT NULL UNIQUE,
    name CHAR(255) NULL,
    rating BIGINT NULL
);

INSERT INTO purchaser(lastname, name, rating) VALUES
('Roshchina','A.I.', 5),
('Petrov','V.A.', 5),
('Kuppe','R.O.', 4);

CREATE TABLE IF NOT EXISTS car
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    stage INT NOT NULL,
    purchaser_id BIGINT NULL,
    FOREIGN KEY (purchaser_id) REFERENCES purchaser (id)
);

INSERT INTO car(name, stage, purchaser_id) VALUES
('GAZ 2752', 2008, 1),
('GAZ 2217', 2012, 2),
('Mercedes S6', 2015, 2);