CREATE TABLE viewing_history
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT REFERENCES users (id),
    movie_id   BIGINT REFERENCES movies (id),
    watched_at TIMESTAMPTZ DEFAULT now()
);