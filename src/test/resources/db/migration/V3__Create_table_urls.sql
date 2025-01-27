CREATE TABLE IF NOT EXISTS url_shortener.urls
(
    id         BIGSERIAL PRIMARY KEY,
    long_url   VARCHAR(2000)                       NOT NULL,
    short_url  VARCHAR(50) UNIQUE                  NOT NULL,
    visits     BIGINT    DEFAULT 0                 NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    expires_at TIMESTAMP,
    user_id    BIGINT                              NOT NULL,
    FOREIGN KEY (user_id) REFERENCES url_shortener.users (id)
);
