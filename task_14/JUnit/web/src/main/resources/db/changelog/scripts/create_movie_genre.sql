CREATE TABLE movie_genre
(
    genre_id BIGINT REFERENCES genres (id),
    movie_id BIGINT REFERENCES movies (id),
    PRIMARY KEY (genre_id, movie_id)
);