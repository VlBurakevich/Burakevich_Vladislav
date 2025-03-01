package org.senla.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Credential;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.WatchingList;
import org.senla.exceptions.DatabaseDeleteException;
import org.senla.repository.MovieRepository;
import org.senla.repository.UserRepository;
import org.senla.repository.WatchingListRepository;
import org.senla.service.WatchingListService;
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
class WatchingListServiceIntegrationTest {

    @Autowired
    private WatchingListService watchingListService;

    @Autowired
    private WatchingListRepository watchingListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        watchingListRepository.deleteAll();
        userRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    void testInsertWatchingList() {
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

        watchingListService.insert(movie.getId(), user.getId());

        List<WatchingList> watchList = watchingListRepository.findAll();
        assertFalse(watchList.isEmpty());
        assertEquals(movie.getId(), watchList.get(0).getMovie().getId());
        assertEquals(user.getId(), watchList.get(0).getUser().getId());
        assertNotNull(watchList.get(0).getAddedAt());
    }

    @Test
    void testGetWatchLaterMovies() {
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

        watchingListService.insert(movie.getId(), user.getId());

        List<MoviePreviewDto> watchLaterMovies = watchingListService.getWatchLaterMovies(user.getId());
        assertFalse(watchLaterMovies.isEmpty());
        assertEquals("Test Movie", watchLaterMovies.get(0).getTitle());
    }

    @Test
    void testRemoveFromWatchLaterSuccess() {
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

        watchingListService.insert(movie.getId(), user.getId());

        watchingListService.removeFromWatchLater(movie.getId(), user.getId());

        List<WatchingList> watchList = watchingListRepository.findAll();
        assertTrue(watchList.isEmpty());
    }

    @Test
    void testRemoveFromWatchLaterFailure() {
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
                watchingListService.removeFromWatchLater(invalidMovieId, validUserId));
    }
}
