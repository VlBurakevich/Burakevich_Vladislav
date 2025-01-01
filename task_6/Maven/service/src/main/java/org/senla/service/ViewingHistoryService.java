package org.senla.service;

import org.senla.dao.MovieDao;
import org.senla.dao.UserDao;
import org.senla.dao.ViewingHistoryDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.dto.MoviePreviewDto;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.ViewingHistory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ViewingHistoryService {
    @Autowired
    private ViewingHistoryDao viewingHistoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieService movieService;

    public ViewingHistory getById(Long id) {
        return viewingHistoryDao.getById(id);
    }

    public List<ViewingHistory> getAll() {
        return viewingHistoryDao.getAll();
    }

    public void update(ViewingHistory viewingHistory) {
        viewingHistoryDao.update(viewingHistory);
    }

    public void delete(Long id) {
        viewingHistoryDao.delete(id);
    }

    public void insert(Long movieId, String username) {
        ViewingHistory viewingHistory = new ViewingHistory();

        viewingHistory.setMovie(movieDao.getById(movieId));
        viewingHistory.setUser(userDao.getByUsername(username));
        viewingHistory.setWatchedAt(Timestamp.valueOf(LocalDateTime.now()));
        viewingHistoryDao.insert(viewingHistory);
    }

    public List<MoviePreviewDto> getViewingHistoryMovies(String username) {
        User user = userDao.getByUsername(username);
        System.out.println("User read");
        List<Movie> viewingMovies = viewingHistoryDao.getMoviesByUser(user);
        System.out.println("Movie read");
        return movieService.convertMoviesToPreviewDto(viewingMovies);
    }

    public void removeFromViewingHistory(Long movieId, String username) {
        User user = userDao.getByUsername(username);
        Movie movie = movieDao.getById(movieId);
        viewingHistoryDao.removeMovieFromHistory(user, movie);
    }
}
