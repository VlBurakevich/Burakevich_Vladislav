package org.senla.repository;

import org.senla.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m ORDER BY FUNCTION('RANDOM') LIMIT :limit")
    List<Movie> findLimitedRandom(@Param("limit") int limit);
}
