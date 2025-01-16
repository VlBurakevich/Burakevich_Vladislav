package org.senla.service;

import lombok.AllArgsConstructor;
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

    public void insert(Long movieId, String username) {
        ViewingHistory viewingHistory = new ViewingHistory();

        viewingHistory.setMovie(movieRepository.getById(movieId));
        viewingHistory.setUser(userRepository.getByUsername(username));
        viewingHistory.setWatchedAt(Timestamp.valueOf(LocalDateTime.now()));
        viewingHistoryRepository.save(viewingHistory);
    }

    public List<MoviePreviewDto> getViewingHistoryMovies(String username) {
        User user = userRepository.getByUsername(username);

        List<Movie> viewingMovies = viewingHistoryRepository.getMoviesByUser(user);

        return movieService.convertMoviesToPreviewDto(viewingMovies);
    }

    public void removeFromViewingHistory(Long movieId, String username) {
        User user = userRepository.getByUsername(username);
        Movie movie = movieRepository.getById(movieId);
        viewingHistoryRepository.removeMovieFromHistory(user, movie);
    }
}
