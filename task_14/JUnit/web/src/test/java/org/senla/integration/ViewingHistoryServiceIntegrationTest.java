package org.senla.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Credential;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.ViewingHistory;
import org.senla.exceptions.DatabaseDeleteException;
import org.senla.repository.MovieRepository;
import org.senla.repository.UserRepository;
import org.senla.repository.ViewingHistoryRepository;
import org.senla.service.ViewingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
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
class ViewingHistoryServiceIntegrationTest {

    @Autowired
    private ViewingHistoryService viewingHistoryService;

    @Autowired
    private ViewingHistoryRepository viewingHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        viewingHistoryRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testInsertViewingHistory() {
        User user = new User();
        user.setUsername("testUser");

        Credential credential = new Credential();
        credential.setEmail("test@test.com");
        credential.setPassword("password");
        user.setCredential(credential);

        userRepository.save(user);

        Movie movie = new Movie();
        movie.setTitle("title");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2023-01-01"));
        movieRepository.save(movie);

        viewingHistoryService.insert(movie.getId(), user.getId());

        List<ViewingHistory> history = viewingHistoryRepository.findAll();
        assertFalse(history.isEmpty());
        assertEquals(movie.getId(), history.get(0).getMovie().getId());
        assertEquals(user.getId(), history.get(0).getUser().getId());
        assertNotNull(history.get(0).getWatchedAt());
    }

    @Test
    void testGetViewingHistoryMovies() {
        User user = new User();
        user.setUsername("testUser");

        Credential credential = new Credential();
        credential.setEmail("test@test.com");
        credential.setPassword("password");
        user.setCredential(credential);
        userRepository.save(user);

        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2023-01-01"));
        movieRepository.save(movie);

        viewingHistoryService.insert(movie.getId(), user.getId());

        List<MoviePreviewDto> historyMovies = viewingHistoryService.getViewingHistoryMovies(user.getId());
        assertFalse(historyMovies.isEmpty());
        assertEquals("Test Movie", historyMovies.get(0).getTitle());
    }

    @Test
    void testRemoveFromViewingHistorySuccess() {
        User user = new User();
        user.setUsername("testUser");

        Credential credential = new Credential();
        credential.setEmail("test@test.com");
        credential.setPassword("password");
        user.setCredential(credential);

        userRepository.save(user);

        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2023-01-01"));
        movieRepository.save(movie);

        viewingHistoryService.insert(movie.getId(), user.getId());

        viewingHistoryService.removeFromViewingHistory(movie.getId(), user.getId());

        List<ViewingHistory> history = viewingHistoryRepository.findAll();
        assertTrue(history.isEmpty());
    }

    @Test
    void testRemoveFromViewingHistoryFailure() {
        User user = new User();
        user.setUsername("testUser");

        Credential credential = new Credential();
        credential.setEmail("test@test.com");
        credential.setPassword("password");
        user.setCredential(credential);

        userRepository.save(user);

        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDuration(7200);
        movie.setReleaseDate(Date.valueOf("2023-01-01"));

        movieRepository.save(movie);

        Long invalidMovieId = 999L;
        Long validUserId = user.getId();

        assertThrows(DatabaseDeleteException.class, () ->
                viewingHistoryService.removeFromViewingHistory(invalidMovieId, validUserId));
    }

}
