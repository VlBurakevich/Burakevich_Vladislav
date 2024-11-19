CREATE TABLE users (
	id BIGSERIAL PRIMERY KEY,
	username VARCHAR(50) NOT NULL
);

CREATE TABLE user_role (
	user_id BIGINT REFERENCES users(id),
	role_id BIGINT REFERENCES roles(id),
	PRIMARY KEY (user_id, role_id)
);

CREATE TABLE roles (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(20) NOT NULL
);

CREATE TABLE credentails (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES user(id),
	password VARCHAR(20) NOT NULL,
	email VARCHAR(150) NOT NULL
);

CREATE TABLE viewong_history (
	user_id BIGINT REFERENCES users(id),
	movie_id BIGINT REFERENCES movies(id),
	watched_at TIMESTAMP,
	PRIMARY KEY (user_id, movie_id)
);

CREATE TABLE watching_list (
	user_id BIGINT REFERENCES users(id),
	movie_id BIGINT REFERENCES movies(id),
	added_at TIMESTAMP,
	PRIMARY KEY (user_id, movie_id)
);

CREATE TABLE movies (
	id BIGSERIAL PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	description TEXT,
	release_date DATE
);

CREATE TABLE reviews (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES users(id),
	movie_id BIGINT REFERENCES movies(id),
	rating SMALLINT CHECK (rating BETWEEN 1 AND 5),
	comment TEXT
);

CREATE TABLE genres (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	description TEXT
);

CREATE TABLE movie_genres (
	id BIGSERIAL PRIMARY KEY,
	parent_genre_id BIGINT REFERENCES genres(id),
	genre_id BIGINT REFERENCES genres(id),
	movie_id BIGINT REFERENCES movies(id),
);

CREATE TABLE producer (
	id BIGSERIAL PRIMARY KEY,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	description TEXT
);

CREATE TABLE movies_prducers (
	producer_id BIGINT REFERENCES producers(id),
	movie_id BIGINT REFERENCES movies(id),
	PRIMARY KEY (producer_id, member_id)
); 

CREATE TABLE move_member (
	movie_id BIGINT REFERENCES movies(id),
	member_id BIGINT,
	role ENUM('actor', 'producer')
	PRIMARY KEY (movie_id, member_id)
);

CREATE TABLE movies_actors (
	atror_id BIGINT REFERENCES actors(id),
	movie_id BIGINT REFERENCES actors(id),
	PRIMARY KEY (actor_id, movie_id)
);

CREATE TABLE actors (
	id BIGSERIAL PRIMARY KEY,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	nationality VARCHAR(50),
	gender BOOLEAN
);
