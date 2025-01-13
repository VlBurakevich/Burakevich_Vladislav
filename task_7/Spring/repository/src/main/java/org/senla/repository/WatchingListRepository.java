package org.senla.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.senla.di.annotations.Component;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.WatchingList;
import org.senla.exceptions.DatabaseDeleteException;
import org.senla.exceptions.DatabaseGetException;

import java.util.List;

@Component
public class WatchingListRepository extends GenericRepository<WatchingList, Long> {

    public WatchingListRepository() {
        super(WatchingList.class);
    }

    public List<Movie> getMoviesByUser(User user) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
            Root<WatchingList> root = cq.from(WatchingList.class);
            Join<WatchingList, Movie> movieJoin = root.join("movie");

            cq.select(movieJoin).where(cb.equal(root.get("user"), user));

            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new DatabaseGetException(Movie.class.getSimpleName());
        }
    }

    public void removeMovieFromList(User user, Movie movie) {
        try {
            List<WatchingList> histories = findViewingHistories(user, movie);

            if (!histories.isEmpty()) {
                entityManager.getTransaction().begin();
                for (WatchingList history : histories) {
                    entityManager.remove(history);

                }
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            throw new DatabaseDeleteException(WatchingList.class.getSimpleName());
        }
    }

    private List<WatchingList> findViewingHistories(User user, Movie movie) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WatchingList> cq = cb.createQuery(WatchingList.class);
        Root<WatchingList> root = cq.from(WatchingList.class);

        cq.select(root)
                .where(cb.equal(root.get("user"), user),
                        cb.equal(root.get("movie"), movie));

        return entityManager.createQuery(cq).getResultList();
    }
}
