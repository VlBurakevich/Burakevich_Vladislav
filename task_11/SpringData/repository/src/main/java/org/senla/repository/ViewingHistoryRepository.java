package org.senla.repository;

import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.ViewingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ViewingHistoryRepository extends JpaRepository<ViewingHistory, Long> {
    @Query("SELECT vh.movie FROM ViewingHistory vh WHERE vh.user = :user")
    List<Movie> findMoviesByUser(@Param("user") User user);

    List<ViewingHistory> findByUserAndMovie(User user, Movie movie);

    @Transactional
    void deleteByUserAndMovie(User user, Movie movie);
}
