INSERT INTO users (username) VALUES
('johndoe'),
('janedoe'),
('moviebuff'),
('critic101'),
('superfan');

INSERT INTO roles (name) VALUES
('viewer'),
('admin'),
('critic');

INSERT INTO user_role (user_id, role_id) VALUES
(1, 1), 
(2, 1), 
(3, 2), 
(4, 3), 
(5, 1), 
(5, 3);

INSERT INTO credentials (user_id, password, email) VALUES
(1, 'pass123', 'johndoe@example.com'),
(2, 'securpass', 'janedoe@example.com'),
(3, 'movielover', 'moviebuff@example.com'),
(4, 'criticpass', 'critic101@example.com'),
(5, 'fanatic123', 'superfan@example.com');

INSERT INTO movies (title, description, duration, release_date) VALUES
('Inception', 'A mind-bending thriller about dreams within dreams.', '02:28:00', '2010-07-16'),
('Interstellar', 'A space exploration adventure to save humanity.', '02:49:00', '2014-11-07'),
('The Godfather', 'The story of an Italian-American crime family.', '02:55:00', '1972-03-24'),
('The Dark Knight', 'Batman faces his greatest foe, the Joker.', '02:32:00', '2008-07-18'),
('Forrest Gump', 'A man with a low IQ recounts his extraordinary life.', '02:22:00', '1994-07-06'),
('The Shawshank Redemption', 'Two imprisoned men bond over years.', '02:22:00', '1994-09-23');

INSERT INTO genres (parent_genre_id, name, description) VALUES 
(NULL, 'Drama', 'Movies with serious and intense themes'),
(NULL, 'Comedy', 'Movies designed to make people laugh'),
(NULL, 'Action', 'Movies with high energy and stunts'),
(NULL, 'Thriller', 'Movies that are suspenseful and intense'),
(NULL, 'Science Fiction', 'Movies based on futuristic and sci-fi themes'),
(NULL, 'Fantasy', 'Movies with magical and fantastical elements'),
(NULL, 'Documentary', 'Non-fictional films'),
(NULL, 'Horror', 'Movies designed to frighten the audience');

INSERT INTO movie_genres (genre_id, movie_id) VALUES 
(1, 1), 
(5, 2), 
(3, 3), 
(4, 4), 
(1, 5), 
(7, 6);

INSERT INTO producers (first_name, last_name, description) VALUES 
('Christopher', 'Nolan', 'Renowned director known for mind-bending films'), 
('Francis', 'Ford Coppola', 'Director of iconic films'), 
('Steven', 'Spielberg', 'Famous director and producer of blockbuster films'), 
('Quentin', 'Tarantino', 'Known for unique style and direction');

INSERT INTO movies_producers (producer_id, movie_id) VALUES 
(1, 1), 
(1, 2),
(2, 3),
(3, 4),
(4, 5);

INSERT INTO actors (first_name, last_name, nationality, gender) VALUES 
('Leonardo', 'DiCaprio', 'USA', 'Male'),
('Matthew', 'McConaughey', 'USA', 'Male'),
('Al', 'Pacino', 'USA', 'Male'),
('Morgan', 'Freeman', 'USA', 'Male'),
('Natalie', 'Portman', 'Israel', 'Female'),
('Brad', 'Pitt', 'USA', 'Male');

INSERT INTO movies_actors (actor_id, movie_id) VALUES 
(1, 1), 
(2, 2),
(3, 3),
(4, 6),
(5, 1),
(6, 5);

INSERT INTO viewing_history (user_id, movie_id, watched_at) VALUES 
(1, 1, '2023-01-01 12:00:00'),
(2, 2, '2023-01-02 15:00:00'),
(3, 3, '2023-01-03 18:00:00'),
(1, 4, '2023-01-04 20:30:00'),
(5, 5, '2023-01-05 22:45:00');

INSERT INTO watching_list (user_id, movie_id, added_at) VALUES 
(1, 2, '2023-01-06 10:00:00'),
(2, 3, '2023-01-07 14:00:00'),
(3, 5, '2023-01-08 17:00:00'),
(4, 1, '2023-01-09 19:30:00'),
(5, 4, '2023-01-10 21:15:00');

INSERT INTO reviews (user_id, movie_id, rating, comment) VALUES 
(1, 1, 5, 'Amazing movie with a great concept!'),
(2, 2, 4, 'Loved the visuals and storyline.'),
(3, 3, 5, 'Classic movie, a must-watch.'),
(4, 4, 4, 'Excellent portrayal of characters.'),
(5, 5, 3, 'Interesting story but a bit slow.');
