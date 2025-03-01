package org.senla.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.dto.ReviewDto;
import org.senla.entity.Movie;
import org.senla.entity.Review;
import org.senla.entity.User;
import org.senla.exceptions.DatabaseSaveException;
import org.senla.repository.MovieRepository;
import org.senla.repository.ReviewRepository;
import org.senla.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    private ReviewDto reviewDto;
    private Movie movie;
    private User user;

    @BeforeEach
    void setUp() {
        reviewDto = new ReviewDto(1L, "test_user", "Great movie!", 5);

        movie = new Movie();
        movie.setId(1L);

        user = new User();
        user.setUsername("test_user");
    }

    @Test
    void saveReview_Success() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(userRepository.findByUsername("test_user")).thenReturn(Optional.of(user));
        when(reviewRepository.save(any(Review.class))).thenReturn(new Review());

        reviewService.saveReview(reviewDto);

        verify(movieRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findByUsername("test_user");
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void saveReview_ThrowsDatabaseSaveException_WhenMovieNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DatabaseSaveException.class, () -> reviewService.saveReview(reviewDto));

        verify(movieRepository, times(1)).findById(1L);
        verify(userRepository, never()).findByUsername(anyString());
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void saveReview_ThrowsDatabaseSaveException_WhenUserNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(userRepository.findByUsername("test_user")).thenReturn(Optional.empty());

        assertThrows(DatabaseSaveException.class, () -> reviewService.saveReview(reviewDto));

        verify(movieRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findByUsername("test_user");
        verify(reviewRepository, never()).save(any(Review.class));
    }
}

