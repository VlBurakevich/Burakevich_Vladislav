package org.senla.repository;

import org.senla.entity.Movie;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository extends GenericRepository<Movie, Long> {

    public MovieRepository() {
        super(Movie.class);
    }
}
