package org.senla.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.WatchingList;
import org.senla.repository.MovieRepository;
import org.senla.repository.UserRepository;
import org.senla.repository.WatchingListRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WatchingListService {
    MovieRepository movieRepository;
    UserRepository userRepository;
    private WatchingListRepository watchingListRepository;
    private MovieService movieService;

    public WatchingList getById(Long id) {
        return watchingListRepository.getById(id);
    }

    public List<WatchingList> getAll() {
        return watchingListRepository.fetchLimitedRandom(20);
    }


    public void update(WatchingList watchingList) {
        watchingListRepository.update(watchingList);
    }

    public void delete(Long id) {
        watchingListRepository.deleteById(id);
    }

    public void insert(Long movieId, Long userId) {
        log.info("Adding movie ID: {} to watch later list for user ID: {}", movieId, userId);

        WatchingList watchingList = new WatchingList();
        watchingList.setMovie(movieRepository.getById(movieId));
        watchingList.setUser(userRepository.getById(userId));
        watchingList.setAddedAt(Timestamp.valueOf(LocalDateTime.now()));
        watchingListRepository.save(watchingList);
    }

    public List<MoviePreviewDto> getWatchLaterMovies(Long userId) {
        log.info("Fetching watch later movies for user ID: {}", userId);
        User user = userRepository.getById(userId);
        List<Movie> watchLaterMovies = watchingListRepository.getMoviesByUser(user);

        return movieService.convertMoviesToPreviewDto(watchLaterMovies);
    }

    public void removeFromWatchLater(Long movieId, Long userId) {
        log.info("Removing movie ID: {} from watch later list for user ID: {}", movieId, userId);
        User user = userRepository.getById(userId);
        Movie movie = movieRepository.getById(movieId);
        watchingListRepository.removeMovieFromList(user, movie);
    }
}
