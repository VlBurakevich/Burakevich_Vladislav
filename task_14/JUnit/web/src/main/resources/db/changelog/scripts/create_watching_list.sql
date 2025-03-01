CREATE TABLE watching_list
(
    id       BIGSERIAL PRIMARY KEY,
    user_id  BIGINT REFERENCES users (id),
    movie_id BIGINT REFERENCES movies (id),
    added_at TIMESTAMPTZ DEFAULT now()
);