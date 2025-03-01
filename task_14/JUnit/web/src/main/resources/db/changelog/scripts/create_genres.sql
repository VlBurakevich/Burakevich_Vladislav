CREATE TABLE genres
(
    id              BIGSERIAL PRIMARY KEY,
    parent_genre_id BIGINT REFERENCES genres (id),
    name            VARCHAR(30) NOT NULL,
    description     VARCHAR(299)
);