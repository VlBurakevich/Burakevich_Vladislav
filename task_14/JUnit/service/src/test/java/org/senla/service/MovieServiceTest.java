package org.senla.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.dto.MovieAddDto;
import org.senla.dto.MovieInfoDto;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Movie;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.DatabaseGetException;
import org.senla.exceptions.DatabaseSaveException;
import org.senla.repository.GenreRepository;
import org.senla.repository.MemberRepository;
import org.senla.repository.MovieRepository;
import org.senla.repository.ReviewRepository;

import java.sql.Date;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
        movie.setDescription("Description");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2025-01-01"));
    }

    @Test
    void getMovies_ShouldReturnMoviePreviewList() {
        when(movieRepository.findLimitedRandom(20)).thenReturn(List.of(movie));

        List<MoviePreviewDto> result = movieService.getMovies();

        assertEquals(1, result.size());
        assertEquals(movie.getTitle(), result.get(0).getTitle());
    }

    @Test
    void getMovieInfoById_ShouldReturnMovieInfo() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.getAllByMovieId(1L)).thenReturn(List.of());
        when(genreRepository.getAllByMovieId(1L)).thenReturn(List.of());
        when(memberRepository.getAllByMovieId(1L)).thenReturn(List.of());

        MovieInfoDto result = movieService.getMovieInfoById(1L);

        assertNotNull(result);
        assertEquals(movie.getTitle(), result.getTitle());
        assertEquals(Duration.ofSeconds(movie.getDuration()), result.getDuration());
    }

    @Test
    void getMovieInfoById_WhenMovieNotFound_ShouldThrowException() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DatabaseGetException.class, () -> movieService.getMovieInfoById(1L));
    }

    @Test
    void addNewMovie_ShouldSaveMovie() {
        MovieAddDto movieAddDto = new MovieAddDto("NewMovie", "Good movie", "PT2H", "2023-01-01", "Action", List.of());

        assertDoesNotThrow(() -> movieService.addNewMovie(movieAddDto));
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void addNewMovie_WhenException_ShouldThrowDatabaseSaveException() {
        MovieAddDto movieAddDto = new MovieAddDto("NewMovie", "Good movie", "PT2H", "2023-01-01", "Action", List.of());

        doThrow(new RuntimeException()).when(movieRepository).save(any(Movie.class));

        assertThrows(DatabaseSaveException.class, () -> movieService.addNewMovie(movieAddDto));
    }

    @Test
    void deleteMovie_ShouldReturnTrue() {
        doNothing().when(movieRepository).deleteById(1L);

        assertTrue(movieService.deleteMovie(1L));
    }

    @Test
    void deleteMovie_WhenException_ShouldThrowDatabaseException() {
        doThrow(new RuntimeException()).when(movieRepository).deleteById(1L);

        assertThrows(DatabaseException.class, () -> movieService.deleteMovie(1L));
    }
}
