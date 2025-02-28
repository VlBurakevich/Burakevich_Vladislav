package org.senla.integration;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.senla.dto.MemberDto;
import org.senla.dto.MovieAddDto;
import org.senla.dto.MovieInfoDto;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Movie;
import org.senla.enums.GenderType;
import org.senla.enums.MemberType;
import org.senla.exceptions.DatabaseGetException;
import org.senla.exceptions.DatabaseSaveException;
import org.senla.repository.GenreRepository;
import org.senla.repository.MemberRepository;
import org.senla.repository.MovieRepository;
import org.senla.repository.ReviewRepository;
import org.senla.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class MovieServiceIntegrationTest {
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private GenreRepository genreRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
        reviewRepository.deleteAll();
        genreRepository.deleteAll();
        memberRepository.deleteAll();
    }
    
    @Test
    void testGetMovies() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2023-01-01"));
        movieRepository.save(movie);
        
        List<MoviePreviewDto> movies = movieService.getMovies();
        assertFalse(movies.isEmpty());
        assertEquals("Test Movie", movies.get(0).getTitle());
    }
    
    @Test 
    void testGetMoviesInfoByIdSuccess() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDescription("Test Description");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2023-01-01"));
        movieRepository.save(movie);
        
        MovieInfoDto movieInfo = movieService.getMovieInfoById(movie.getId());
        assertNotNull(movieInfo);
        assertEquals("Test Movie", movieInfo.getTitle());
        assertEquals("Test Description", movieInfo.getDescription());
    }

    @Test
    void testGetMovieInfoByIdNotFound() {
        assertThrows(DatabaseGetException.class, () -> movieService.getMovieInfoById(999L));
    }

    @Test
    @Transactional
    void testAddNewMovie() {
        MovieAddDto movieAddDto = new MovieAddDto();
        movieAddDto.setTitle("New Movie");
        movieAddDto.setDescription("New Description");
        movieAddDto.setDuration("PT2H");
        movieAddDto.setReleaseDate("2023-01-01");
        movieAddDto.setGenreNames("Action, Drama");
        movieAddDto.setMembers(List.of(new MemberDto(null, "John", "Doe", "AMERICAN", MemberType.ACTOR , GenderType.MALE )));

        movieService.addNewMovie(movieAddDto);

        Movie savedMovie = movieRepository.findById(movieRepository.findAll().get(0).getId()).orElse(null);
        assertNotNull(savedMovie);
        assertEquals("New Movie", savedMovie.getTitle());
        assertEquals(7200, savedMovie.getDuration());
        assertEquals(2, savedMovie.getGenres().size());
        assertEquals(1, savedMovie.getMembers().size());
    }

    @Test
    void testAddNewMovieFailure() {
        MovieAddDto invalidDto = new MovieAddDto();
        invalidDto.setDuration("INVALID");

        assertThrows(DatabaseSaveException.class, () -> movieService.addNewMovie(invalidDto));
    }

    @Test
    void testDeleteMovie() {
        Movie movie = new Movie();
        movie.setTitle("Delete Movie");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2023-01-01"));
        movieRepository.save(movie);

        boolean result = movieService.deleteMovie(movie.getId());
        assertTrue(result);
        assertFalse(movieRepository.findById(movie.getId()).isPresent());
    }
}
