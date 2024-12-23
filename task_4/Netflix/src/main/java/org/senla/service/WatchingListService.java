package org.senla.service;

import org.senla.dao.MovieDao;
import org.senla.dao.UserDao;
import org.senla.dao.WatchingListDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.WatchingList;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class WatchingListService {
    @Autowired
    MovieDao movieDao;

    @Autowired
    UserDao userDao;

    @Autowired
    private WatchingListDao watchingListDao;

    @Autowired
    private MovieService movieService;

    public WatchingList getById(Long id) {
        return watchingListDao.getById(id);
    }

    public List<WatchingList> getAll() {
        return watchingListDao.getAll();
    }


    public void update(WatchingList watchingList) {
        watchingListDao.update(watchingList);
    }

    public void delete(Long id) {
        watchingListDao.delete(id);
    }

    public void insert(Long movieId, String username) {
        WatchingList watchingList = new WatchingList();

        watchingList.setMovie(movieDao.getById(movieId));
        watchingList.setUser(userDao.getByUsername(username));
        watchingList.setAddedAt(Timestamp.valueOf(LocalDateTime.now()));
        watchingListDao.insert(watchingList);
    }

    public List<MoviePreviewDto> getWatchLaterMovies(String username) {
        User user = userDao.getByUsername(username);

        List<Movie> watchLaterMovies = watchingListDao.getMoviesByUser(user);

        return movieService.convertMoviesToPreviewDto(watchLaterMovies);
    }

    public void removeFromWatchLater(Long movieId, String username) {
        User user = userDao.getByUsername(username);
        Movie movie = movieDao.getById(movieId);
        watchingListDao.removeMovieFromList(user, movie);
    }
}
