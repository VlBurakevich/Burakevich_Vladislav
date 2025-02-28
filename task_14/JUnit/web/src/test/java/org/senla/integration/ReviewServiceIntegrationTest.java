package org.senla.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.senla.dto.ReviewDto;
import org.senla.entity.Credential;
import org.senla.entity.Movie;
import org.senla.entity.Review;
import org.senla.entity.User;
import org.senla.exceptions.DatabaseSaveException;
import org.senla.repository.MovieRepository;
import org.senla.repository.ReviewRepository;
import org.senla.repository.UserRepository;
import org.senla.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp() {
        reviewRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testSaveReviewSuccess() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2023-01-01"));
        movieRepository.save(movie);

        User user = new User();
        user.setUsername("testUser");

        Credential credential = new Credential();
        credential.setEmail("test@test.com");
        credential.setPassword("password");
        user.setCredential(credential);
        userRepository.save(user);

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieId(movie.getId());
        reviewDto.setUsername("testUser");
        reviewDto.setRating(5);
        reviewDto.setReviewText("Great movie!");

        reviewService.saveReview(reviewDto);

        Review savedReview = reviewRepository.findByMovieIdAndUsername(movie.getId(), "testUser");
        assertNotNull(savedReview);
        assertEquals(5, savedReview.getRating());
        assertEquals("Great movie!", savedReview.getComment());
    }

    @Test
    void testSaveReviewFailureInvalidMovieId() {
        User user = new User();
        user.setUsername("testUser");

        Credential credential = new Credential();
        credential.setEmail("testmail@gmail.com");
        credential.setPassword("password");
        user.setCredential(credential);
        userRepository.save(user);

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieId(999L);
        reviewDto.setUsername("testUser");
        reviewDto.setRating(4);
        reviewDto.setReviewText("Good!");

        assertThrows(DatabaseSaveException.class, () -> reviewService.saveReview(reviewDto));
    }

    @Test
    void testSaveReviewFailureInvalidUsername() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2023-01-01"));
        movieRepository.save(movie);

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieId(movie.getId());
        reviewDto.setUsername("nonexistent");
        reviewDto.setRating(3);
        reviewDto.setReviewText("Okay movie");

        assertThrows(DatabaseSaveException.class, () -> reviewService.saveReview(reviewDto));
    }
}
