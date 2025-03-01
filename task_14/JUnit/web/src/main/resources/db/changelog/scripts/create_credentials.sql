CREATE TABLE credentials
(
    id       BIGSERIAL PRIMARY KEY,
    password VARCHAR(127) NOT NULL,
    email    VARCHAR(255) NOT NULL
);