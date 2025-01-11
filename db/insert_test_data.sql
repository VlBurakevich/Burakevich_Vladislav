INSERT INTO roles (name) VALUES
('admin'),
('user');

INSERT INTO credentials (password, email) VALUES
('password1', 'user1@example.com'),
('password2', 'user2@example.com'),
('password3', 'user3@example.com'),
('password4', 'user4@example.com');

INSERT INTO users (username, credential_id) VALUES
('user1', 1),
('user2', 2),
('user3', 3),
('user4', 4);

INSERT INTO user_role (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 1),
(4, 2);

INSERT INTO movies (title, description, duration, release_date) VALUES
('Inception', 'A mind-bending thriller.', 8880, '2010-07-16'),
('The Matrix', 'A hacker discovers the true nature of reality.', 8160, '1999-03-31'),
('Interstellar', 'A journey through space and time.', 10140, '2014-11-07'),
('The Dark Knight', 'The battle between Batman and Joker.', 9120, '2008-07-18');

INSERT INTO viewing_history (user_id, movie_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(1, 4),
(2, 3),
(3, 2),
(4, 1);

INSERT INTO watching_list (user_id, movie_id) VALUES
(1, 2),
(2, 1),
(3, 4),
(4, 3),
(1, 3),
(2, 4),
(3, 1),
(4, 2);

INSERT INTO reviews (user_id, movie_id, rating, comment) VALUES
(1, 1, 5, 'Amazing movie!'),
(2, 2, 4, 'Great concept!'),
(3, 3, 5, 'Loved the visuals.'),
(4, 4, 5, 'Iconic movie.'),
(1, 3, 4, 'Very interesting.'),
(2, 4, 5, 'Masterpiece!'),
(3, 1, 4, 'Great idea!'),
(4, 2, 4, 'Fantastic!');

INSERT INTO genres (name, description) VALUES
('Action', 'High energy and exciting scenes.'),
('Sci-Fi', 'Exploration of futuristic and scientific concepts.'),
('Drama', 'Emotionally intense and grounded.'),
('Thriller', 'Suspenseful and gripping.');

INSERT INTO movie_genre (genre_id, movie_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(1, 2),
(2, 1),
(3, 4),
(4, 3);

INSERT INTO members (first_name, last_name, nationality, type, gender) VALUES
('Leonardo', 'DiCaprio', 'USA', 'ACTOR', 'MALE'),
('Christopher', 'Nolan', 'UK', 'PRODUCER', 'MALE'),
('Keanu', 'Reeves', 'Canada', 'ACTOR', 'MALE'),
('Anne', 'Hathaway', 'USA', 'ACTOR', 'FEMALE'),
('Christian', 'Bale', 'UK', 'ACTOR', 'MALE'),
('Emma', 'Thomas', 'UK', 'PRODUCER', 'FEMALE');

INSERT INTO movie_member (movie_id, member_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 2),
(3, 4),
(3, 2),
(4, 5),
(4, 6);
