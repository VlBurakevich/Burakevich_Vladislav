CREATE TABLE credentials
(
    id       BIGSERIAL PRIMARY KEY,
    password VARCHAR(128) NOT NULL,
    email    VARCHAR(255) NOT NULL
);