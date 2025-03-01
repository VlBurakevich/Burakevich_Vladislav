package org.senla.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.ViewingHistory;
import org.senla.exceptions.DatabaseDeleteException;
import org.senla.repository.MovieRepository;
import org.senla.repository.UserRepository;
import org.senla.repository.ViewingHistoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViewingHistoryServiceTest {
    @Mock
    private ViewingHistoryRepository viewingHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private ViewingHistoryService viewingHistoryService;

    @Test
    void testInsert() {
        Long movieId = 1L;
        Long userId = 1L;
        when(movieRepository.getReferenceById(movieId)).thenReturn(new Movie());
        when(userRepository.getReferenceById(userId)).thenReturn(new User());

        viewingHistoryService.insert(movieId, userId);

        verify(viewingHistoryRepository).save(any(ViewingHistory.class));
    }

    @Test
    void testGetViewingHistoryMovies() {
        Long userId = 1L;
        User user = new User();
        List<Movie> movies = List.of(new Movie());
        List<MoviePreviewDto> dtos = List.of(new MoviePreviewDto());
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(viewingHistoryRepository.findMoviesByUser(user)).thenReturn(movies);
        when(movieService.convertMoviesToPreviewDto(movies)).thenReturn(dtos);

        List<MoviePreviewDto> result = viewingHistoryService.getViewingHistoryMovies(userId);

        assertEquals(dtos, result);
    }

    @Test
    void testRemoveFromViewingHistory() {
        Long movieId = 1L;
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(new Movie()));

        viewingHistoryService.removeFromViewingHistory(movieId, userId);

        verify(viewingHistoryRepository).deleteByUserAndMovie(any(User.class), any(Movie.class));
    }

    @Test
    void testRemoveFromViewingHistoryThrowsException() {
        Long movieId = 1L;
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(new Movie()));
        doThrow(new RuntimeException()).when(viewingHistoryRepository).deleteByUserAndMovie(any(), any());

        assertThrows(DatabaseDeleteException.class, () -> viewingHistoryService.removeFromViewingHistory(movieId, userId));
    }
}