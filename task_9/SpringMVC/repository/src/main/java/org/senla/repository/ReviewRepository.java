package org.senla.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.senla.entity.Movie;
import org.senla.entity.Review;
import org.senla.exceptions.DatabaseGetException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepository extends GenericRepository<Review, Long> {

    public ReviewRepository() {
        super(Review.class);
    }

    public List<Review> getAllByMovieId(Long movieId) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> reviewRoot = cq.from(Review.class);
            Join<Review, Movie> movieJoin = reviewRoot.join("movie");

            cq.select(reviewRoot).where(cb.equal(movieJoin.get("id"), movieId));

            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new DatabaseGetException(Review.class.getSimpleName());
        }
    }
}
