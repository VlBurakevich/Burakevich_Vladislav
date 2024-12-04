INSERT INTO roles (name) VALUES 
('admin'), 
('user');

INSERT INTO users (username) VALUES 
('alice'), 
('bob'), 
('charlie'), 
('dave'), 
('eve'), 
('frank'), 
('grace'), 
('heidi'), 
('ivan'), 
('judy');

INSERT INTO user_role (user_id, role_id) VALUES 
(1, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(8, 2),
(9, 2),
(10, 2);

INSERT INTO credentials (user_id, password, email) VALUES 
(1, 'password1', 'alice@example.com'),
(2, 'password2', 'bob@example.com'),
(3, 'password3', 'charlie@example.com'),
(4, 'password4', 'dave@example.com'),
(5, 'password5', 'eve@example.com'),
(6, 'password6', 'frank@example.com'),
(7, 'password7', 'grace@example.com'),
(8, 'password8', 'heidi@example.com'),
(9, 'password9', 'ivan@example.com'),
(10, 'password10', 'judy@example.com');

INSERT INTO movies (title, description, duration, release_date) VALUES 
('Inception', 'A mind-bending thriller.', '02:28:00', '2010-07-16'),
('The Matrix', 'A hacker discovers reality.', '02:16:00', '1999-03-31'),
('Interstellar', 'A space exploration epic.', '02:49:00', '2014-11-07'),
('The Dark Knight', 'A battle for Gotham.', '02:32:00', '2008-07-18'),
('Pulp Fiction', 'Crime stories intertwined.', '02:34:00', '1994-10-14'),
('The Shawshank Redemption', 'Hope and friendship.', '02:22:00', '1994-09-23'),
('Forrest Gump', 'Life of an extraordinary man.', '02:22:00', '1994-07-06'),
('Fight Club', 'An underground fight club.', '02:19:00', '1999-10-15'),
('The Godfather', 'Rise of a mafia family.', '02:55:00', '1972-03-24'),
('The Lord of the Rings', 'A quest to destroy a ring.', '03:48:00', '2001-12-19');

INSERT INTO viewing_history (user_id, movie_id, watched_at) VALUES 
(1, 1, '2023-11-01 14:30:00'),
(2, 2, '2023-11-02 16:45:00'),
(3, 3, '2023-11-03 18:00:00'),
(4, 4, '2023-11-04 20:15:00'),
(5, 5, '2023-11-05 22:30:00'),
(6, 6, '2023-11-06 12:00:00'),
(7, 7, '2023-11-07 13:15:00'),
(8, 8, '2023-11-08 15:30:00'),
(9, 9, '2023-11-09 17:45:00'),
(10, 10, '2023-11-10 19:00:00');

INSERT INTO watching_list (user_id, movie_id, added_at) VALUES 
(1, 2, DEFAULT),
(2, 3, DEFAULT),
(3, 4, DEFAULT),
(4, 5, DEFAULT),
(5, 6, DEFAULT),
(6, 7, DEFAULT),
(7, 8, DEFAULT),
(8, 9, DEFAULT),
(9, 10, DEFAULT),
(10, 1, DEFAULT);

INSERT INTO reviews (user_id, movie_id, rating, comment) VALUES 
(1, 1, 5, 'Amazing movie!'),
(2, 2, 4, 'Very interesting.'),
(3, 3, 5, 'A masterpiece!'),
(4, 4, 4, 'Great action.'),
(5, 5, 3, 'Good, but not my favorite.'),
(6, 6, 5, 'Heartwarming.'),
(7, 7, 4, 'Really liked it.'),
(8, 8, 5, 'Brilliant.'),
(9, 9, 4, 'Classic.'),
(10, 10, 5, 'A must-watch.');

INSERT INTO genres (name, description, parent_genre_id) VALUES 
('Action', 'High-intensity scenes and fights.', NULL),
('Drama', 'Emotional and character-driven.', NULL),
('Science Fiction', 'Futuristic and imaginative.', 1),
('Fantasy', 'Magical and otherworldly.', NULL),
('Crime', 'Stories about criminals.', 2),
('Comedy', 'Light-hearted and funny.', NULL),
('Thriller', 'Suspenseful and gripping.', 1),
('Adventure', 'Exciting journeys and quests.', 4);

INSERT INTO movie_genres (genre_id, movie_id) VALUES 
(1, 1), 
(3, 1),
(2, 2), 
(7, 2),
(3, 3), 
(4, 3),
(1, 4), 
(5, 5),
(2, 6), 
(2, 7),
(6, 7), 
(7, 8),
(5, 9), 
(4, 10);

INSERT INTO members (first_name, last_name, nationality, gender, type) VALUES 
('Leonardo', 'DiCaprio', 'American', 'Male', 'actor'),
('Keanu', 'Reeves', 'Canadian', 'Male', 'actor'),
('Matthew', 'McConaughey', 'American', 'Male', 'actor'),
('Christian', 'Bale', 'British', 'Male', 'actor'),
('Uma', 'Thurman', 'American', 'Female', 'actor'),
('Morgan', 'Freeman', 'American', 'Male', 'producer'),
('Tom', 'Hanks', 'American', 'Male', 'producer'),
('Brad', 'Pitt', 'American', 'Male', 'producer'),
('Marlon', 'Brando', 'American', 'Male', 'actor'),
('Elijah', 'Wood', 'American', 'Male', 'actor');

INSERT INTO movie_member (movie_id, member_id) VALUES 
(1, 1), 
(2, 2), 
(3, 3), 
(4, 4), 
(5, 5), 
(9, 9), 
(10, 10),
(6, 6), 
(7, 7), 
(8, 8); 
