package org.senla.repository;

import org.senla.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r JOIN r.movie m WHERE m.id = :movieId")
    List<Review> getAllByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT r FROM Review r WHERE r.movie.id = :movieId AND r.user.username = :username")
    Review findByMovieIdAndUsername(@Param("movieId") Long movieId, @Param("username") String username);
}
