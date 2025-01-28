package org.senla.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.ViewingHistory;
import org.senla.repository.MovieRepository;
import org.senla.repository.UserRepository;
import org.senla.repository.ViewingHistoryRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ViewingHistoryService {
    private ViewingHistoryRepository viewingHistoryRepository;
    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private MovieService movieService;

    public ViewingHistory getById(Long id) {
        return viewingHistoryRepository.getById(id);
    }

    public List<ViewingHistory> getAll() {
        return viewingHistoryRepository.fetchLimitedRandom(20);
    }

    public void update(ViewingHistory viewingHistory) {
        viewingHistoryRepository.update(viewingHistory);
    }

    public void delete(Long id) {
        viewingHistoryRepository.deleteById(id);
    }

    public void insert(Long movieId, Long userId) {
        log.info("Adding movie ID: {} to viewing history for user ID: {}", movieId, userId);

        ViewingHistory viewingHistory = new ViewingHistory();
        viewingHistory.setMovie(movieRepository.getById(movieId));
        viewingHistory.setUser(userRepository.getById(userId));
        viewingHistory.setWatchedAt(Timestamp.valueOf(LocalDateTime.now()));
        viewingHistoryRepository.save(viewingHistory);
    }

    public List<MoviePreviewDto> getViewingHistoryMovies(Long userId) {
        log.info("Fetching viewing history movies for user ID: {}", userId);
        User user = userRepository.getById(userId);
        List<Movie> viewingMovies = viewingHistoryRepository.getMoviesByUser(user);

        return movieService.convertMoviesToPreviewDto(viewingMovies);
    }

    public void removeFromViewingHistory(Long movieId, Long userId) {
        log.info("Removing movie ID: {} from viewing history for user ID: {}", movieId, userId);
        User user = userRepository.getById(userId);
        Movie movie = movieRepository.getById(movieId);
        viewingHistoryRepository.removeMovieFromHistory(user, movie);
    }
}
