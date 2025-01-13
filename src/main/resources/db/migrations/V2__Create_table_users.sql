DROP TABLE IF EXISTS url_shortener.users;
CREATE TABLE url_shortener.users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100)       NOT NULL
);