package org.senla.service;

import org.senla.dao.MovieDao;
import org.senla.entity.Movie;

import java.util.List;

public class MovieService {
    private final MovieDao movieDao;

    public MovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

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
