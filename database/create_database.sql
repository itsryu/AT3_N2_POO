CREATE DATABASE IF NOT EXISTS POO;

USE POO;
  
CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) DEFAULT CHARSET = 'utf8' DEFAULT COLLATE = 'utf8_general_ci';

CREATE TABLE profiles (
    id_profile INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    username VARCHAR(50),
    bio VARCHAR(255),
    profile_picture MEDIUMBLOB NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE
) DEFAULT CHARSET = 'utf8' DEFAULT COLLATE = 'utf8_general_ci';

CREATE TABLE sessions (
    id_session INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    entry_timestamp TIMESTAMP NULL,
    exit_timestamp TIMESTAMP NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE
) DEFAULT CHARSET = 'utf8' DEFAULT COLLATE = 'utf8_general_ci';

CREATE TABLE IF NOT EXISTS friendships (
    id_friendship INT AUTO_INCREMENT PRIMARY KEY,
    id_user_requester INT NOT NULL,               -- Usuário que enviou a solicitação
    id_user_receiver INT NOT NULL,                -- Usuário que recebeu a solicitação
    friendship_status ENUM('pending', 'accepted', 'blocked') DEFAULT 'pending',
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    acceptance_date TIMESTAMP NULL,
    FOREIGN KEY (id_user_requester) REFERENCES users(id_user) ON DELETE CASCADE,
    FOREIGN KEY (id_user_receiver) REFERENCES users(id_user) ON DELETE CASCADE,
    CONSTRAINT unique_friendship UNIQUE (id_user_requester, id_user_receiver)
) DEFAULT CHARSET = 'utf8' DEFAULT COLLATE = 'utf8_general_ci';
