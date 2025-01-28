package org.senla.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.senla.entity.Genre;
import org.senla.entity.Movie;
import org.senla.exceptions.DatabaseGetException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class GenreRepository extends GenericRepository<Genre, Long> {

    public GenreRepository() {
        super(Genre.class);
    }

    public List<Genre> getAllByMovieId(Long movieId) {
        log.info("Getting all genres by movie id {}", movieId);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Genre> cq = cb.createQuery(Genre.class);
            Root<Genre> genreRoot = cq.from(Genre.class);
            Join<Genre, Movie> movie = genreRoot.join("movies");

            cq.select(genreRoot).where(cb.equal(movie.get("id"), movieId));

            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new DatabaseGetException(Genre.class.getSimpleName());
        }
    }
}
