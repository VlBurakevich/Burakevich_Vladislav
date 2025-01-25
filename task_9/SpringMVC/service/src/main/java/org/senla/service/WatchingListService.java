package org.senla.service;

import lombok.AllArgsConstructor;
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

    public void insert(Long movieId, String username) {
        WatchingList watchingList = new WatchingList();

        watchingList.setMovie(movieRepository.getById(movieId));
        watchingList.setUser(userRepository.getByUsername(username));
        watchingList.setAddedAt(Timestamp.valueOf(LocalDateTime.now()));
        watchingListRepository.save(watchingList);
    }

    public List<MoviePreviewDto> getWatchLaterMovies(String username) {
        User user = userRepository.getByUsername(username);

        List<Movie> watchLaterMovies = watchingListRepository.getMoviesByUser(user);

        return movieService.convertMoviesToPreviewDto(watchLaterMovies);
    }

    public void removeFromWatchLater(Long movieId, String username) {
        User user = userRepository.getByUsername(username);
        Movie movie = movieRepository.getById(movieId);
        watchingListRepository.removeMovieFromList(user, movie);
    }
}
