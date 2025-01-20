CREATE TABLE IF NOT EXISTS url_shortener.users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100)       NOT NULL
);
