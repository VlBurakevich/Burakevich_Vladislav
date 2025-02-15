CREATE TYPE member_type AS ENUM ('ACTOR', 'PRODUCER');
CREATE TYPE gender_type AS ENUM ('MALE', 'FEMALE');

CREATE TABLE credentials (
    id BIGSERIAL PRIMARY KEY,
    password VARCHAR(127) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    credential_id BIGINT NOT NULL REFERENCES credentials(id)
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

CREATE TABLE movies (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    duration INTEGER NOT NULL,
    release_date DATE NOT NULL
);

CREATE TABLE viewing_history (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    movie_id BIGINT REFERENCES movies(id),
    watched_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE watching_list (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    movie_id BIGINT REFERENCES movies(id),
    added_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    movie_id BIGINT REFERENCES movies(id),
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    comment VARCHAR(299)
);

CREATE TABLE genres (
    id BIGSERIAL PRIMARY KEY,
    parent_genre_id BIGINT REFERENCES genres(id),
    name VARCHAR(30) NOT NULL,
    description VARCHAR(299)
);

CREATE TABLE movie_genre (
    genre_id BIGINT REFERENCES genres(id),
    movie_id BIGINT REFERENCES movies(id),
    PRIMARY KEY (genre_id, movie_id)
);

CREATE TABLE members (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    nationality VARCHAR(30),
    type member_type NOT NULL,
    gender gender_type
);

CREATE TABLE movie_member (
    movie_id BIGINT REFERENCES movies(id),
    member_id BIGINT REFERENCES members(id),
    PRIMARY KEY (movie_id, member_id)
);
