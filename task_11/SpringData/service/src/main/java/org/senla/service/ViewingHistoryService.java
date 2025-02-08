package org.senla.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.ViewingHistory;
import org.senla.exceptions.DatabaseDeleteException;
import org.senla.repository.MovieRepository;
import org.senla.repository.UserRepository;
import org.senla.repository.ViewingHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void insert(Long movieId, Long userId) {
        ViewingHistory viewingHistory = new ViewingHistory();
        viewingHistory.setMovie(movieRepository.getReferenceById(movieId));
        viewingHistory.setUser(userRepository.getReferenceById(userId));
        viewingHistory.setWatchedAt(Timestamp.valueOf(LocalDateTime.now()));
        viewingHistoryRepository.save(viewingHistory);
    }

    public List<MoviePreviewDto> getViewingHistoryMovies(Long userId) {
        User user = userRepository.getReferenceById(userId);
        List<Movie> viewingMovies = viewingHistoryRepository.findMoviesByUser(user);

        return movieService.convertMoviesToPreviewDto(viewingMovies);
    }

    @Transactional
    public void removeFromViewingHistory(Long movieId, Long userId) {
        try {
            User user = userRepository.getReferenceById(userId);
            Movie movie = movieRepository.getReferenceById(movieId);
            viewingHistoryRepository.deleteByUserAndMovie(user, movie);
        } catch (Exception e) {
            throw new DatabaseDeleteException(ViewingHistory.class.getSimpleName());
        }
    }
}
