package org.senla.repository;

import org.senla.di.annotations.Component;
import org.senla.entity.Movie;

@Component
public class MovieRepository extends GenericRepository<Movie, Long> {

    public MovieRepository() {
        super(Movie.class);
    }
}
