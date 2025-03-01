CREATE TABLE reviews
(
    id       BIGSERIAL PRIMARY KEY,
    user_id  BIGINT REFERENCES users (id),
    movie_id BIGINT REFERENCES movies (id),
    rating   INTEGER CHECK (rating BETWEEN 1 AND 5),
    comment  VARCHAR(299)
);