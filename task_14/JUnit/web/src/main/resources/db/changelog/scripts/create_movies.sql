CREATE TABLE movies
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(100) NOT NULL,
    description  TEXT,
    duration     INTEGER      NOT NULL,
    release_date DATE         NOT NULL
);