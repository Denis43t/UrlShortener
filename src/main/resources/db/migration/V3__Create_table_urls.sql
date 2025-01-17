DROP TABLE IF EXISTS url_shortener.urls;
CREATE TABLE url_shortener.urls
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    long_url  VARCHAR(2000)      NOT NULL,
    short_url VARCHAR(10) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES url_shortener.users(id)
);