CREATE TABLE movie_member
(
    movie_id  BIGINT REFERENCES movies (id),
    member_id BIGINT REFERENCES members (id),
    PRIMARY KEY (movie_id, member_id)
);