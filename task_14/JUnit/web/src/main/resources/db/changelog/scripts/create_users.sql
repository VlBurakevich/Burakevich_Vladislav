CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(50) NOT NULL,
    credential_id BIGINT      NOT NULL REFERENCES credentials (id)
);