package org.senla.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.senla.di.annotations.Component;
import org.senla.entity.Review;
import org.senla.exceptions.DatabaseException;

import java.util.List;

@Component
public class ReviewRepository extends GenericRepository<Review, Long> {

    public ReviewRepository() {
        super(Review.class);
    }

    public List<Review> getAllByMovieId(Long movieId) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> reviewRoot = cq.from(Review.class);

            cq.select(reviewRoot).where(cb.equal(reviewRoot.get("movie"), movieId));

            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.ERROR_GET_ENTITY, Review.class.getSimpleName());
        }
    }
}
