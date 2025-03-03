CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(50)    NOT NULL,
    balance       DECIMAL(10, 2) NOT NULL,
    credential_id BIGINT REFERENCES credentials (id)
);