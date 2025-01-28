package org.senla.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.ViewingHistory;
import org.senla.exceptions.DatabaseDeleteException;
import org.senla.exceptions.DatabaseGetException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
public class ViewingHistoryRepository extends GenericRepository<ViewingHistory, Long> {

    public ViewingHistoryRepository() {
        super(ViewingHistory.class);
    }

    public List<Movie> getMoviesByUser(User user) {
        log.info("Getting movies by user {}", user);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
            Root<ViewingHistory> root = cq.from(ViewingHistory.class);
            Join<ViewingHistory, Movie> movieJoin = root.join("movie");

            cq.select(movieJoin).where(cb.equal(root.get("user"), user));

            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new DatabaseGetException(Movie.class.getSimpleName());
        }
    }

    @Transactional
    public void removeMovieFromHistory(User user, Movie movie) {
        log.info("Removing movie {} from user {}", movie, user);
        try {
            List<ViewingHistory> histories = findViewingHistories(user, movie);

            if (!histories.isEmpty()) {
                for (ViewingHistory history : histories) {
                    entityManager.remove(history);
                }
            }
        } catch (Exception e) {
            throw new DatabaseDeleteException(ViewingHistory.class.getSimpleName());
        }
    }

    private List<ViewingHistory> findViewingHistories(User user, Movie movie) {
        log.info("Finding viewing histories for {}", movie);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ViewingHistory> cq = cb.createQuery(ViewingHistory.class);
        Root<ViewingHistory> root = cq.from(ViewingHistory.class);

        cq.select(root).where(
                cb.equal(root.get("user"), user),
                cb.equal(root.get("movie"), movie)
        );

        return entityManager.createQuery(cq).getResultList();
    }
}
