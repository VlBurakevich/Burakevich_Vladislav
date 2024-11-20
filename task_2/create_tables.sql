CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE user_role (
    user_id BIGINT REFERENCES users(id),
    role_id BIGINT REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE credentials (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    password VARCHAR(32) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE movies (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    duration TIME NOT NULL,
    release_date DATE NOT NULL
);

CREATE TABLE viewing_history (
    user_id BIGINT REFERENCES users(id),
    movie_id BIGINT REFERENCES movies(id),
    watched_at TIMESTAMPTZ DEFAULT now(),
    PRIMARY KEY (user_id, movie_id)
);

CREATE TABLE watching_list (
    user_id BIGINT REFERENCES users(id),
    movie_id BIGINT REFERENCES movies(id),
    added_at TIMESTAMPTZ default now(),
    PRIMARY KEY (user_id, movie_id)
);

CREATE TABLE reviews ( 
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    movie_id BIGINT REFERENCES movies(id),
    rating SMALLINT CHECK (rating BETWEEN 1 AND 5),
    comment VARCHAR(299)
);

CREATE TABLE genres (
    id BIGSERIAL PRIMARY KEY,
    parent_genre_id BIGINT REFERENCES genres(id),
    name VARCHAR(30) NOT NULL,
    description VARCHAR(299)
);

CREATE TABLE movie_genres (
    genre_id BIGINT REFERENCES genres(id),
    movie_id BIGINT REFERENCES movies(id),
    PRIMARY KEY (genre_id, movie_id)
);

CREATE TABLE producers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    description TEXT
);

CREATE TABLE movies_producers (
    producer_id BIGINT REFERENCES producers(id),
    movie_id BIGINT REFERENCES movies(id),
    PRIMARY KEY (producer_id, movie_id)
);

CREATE TYPE member_type AS ENUM('actor', 'producer');

CREATE TABLE movie_member (
    movie_id BIGINT REFERENCES movies(id),
    member_id BIGINT,
    type member_type,
    PRIMARY KEY (movie_id, member_id)
);


CREATE TYPE gender_type AS ENUM('Male', 'Female');

CREATE TABLE actors (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    nationality VARCHAR(30),
    gender gender_type
);

CREATE TABLE movies_actors (
    actor_id BIGINT REFERENCES actors(id),
    movie_id BIGINT REFERENCES movies(id),
    PRIMARY KEY (actor_id, movie_id)
);
