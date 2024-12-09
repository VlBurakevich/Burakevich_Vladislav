package org.senla.service;

import org.senla.dao.MovieDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.entity.Movie;

import java.util.List;

@Component
public class MovieService {

    @Autowired
    private MovieDao movieDao;

    public Movie getById(Long id) {
        return movieDao.getById(id);
    }

    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    public void insert(Movie movie) {
        movieDao.insert(movie);
    }

    public void update(Movie movie) {
        movieDao.update(movie);
    }

    public void delete(Long id) {
        movieDao.delete(id);
    }
}
