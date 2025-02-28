package org.senla.service;

import lombok.AllArgsConstructor;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.WatchingList;
import org.senla.exceptions.DatabaseDeleteException;
import org.senla.repository.MovieRepository;
import org.senla.repository.UserRepository;
import org.senla.repository.WatchingListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class WatchingListService {
    private MovieRepository movieRepository;
    private UserRepository userRepository;
    private WatchingListRepository watchingListRepository;
    private MovieService movieService;

    @Transactional
    public void insert(Long movieId, Long userId) {
        WatchingList watchingList = new WatchingList();
        watchingList.setMovie(movieRepository.getReferenceById(movieId));
        watchingList.setUser(userRepository.getReferenceById(userId));
        watchingList.setAddedAt(Timestamp.valueOf(LocalDateTime.now()));
        watchingListRepository.save(watchingList);
    }

    public List<MoviePreviewDto> getWatchLaterMovies(Long userId) {
        User user = userRepository.getReferenceById(userId);
        List<Movie> watchLaterMovies = watchingListRepository.findMoviesByUser(user);

        return movieService.convertMoviesToPreviewDto(watchLaterMovies);
    }

    @Transactional
    public void removeFromWatchLater(Long movieId, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new DatabaseDeleteException("User not found"));
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new DatabaseDeleteException("Movie not found"));
            watchingListRepository.deleteByUserAndMovie(user, movie);
        } catch (Exception e) {
            throw new DatabaseDeleteException(WatchingList.class.getSimpleName());
        }
    }
}
