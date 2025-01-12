DROP TABLE IF EXISTS url_shortener.user;

CREATE TABLE url_shortener.user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);