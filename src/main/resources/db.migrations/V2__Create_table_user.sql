DROP TABLE IF EXISTS url_shortener.user;

CREATE TABLE url_shortener.user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);