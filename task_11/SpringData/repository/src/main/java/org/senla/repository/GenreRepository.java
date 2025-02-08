package org.senla.repository;

import org.senla.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("SELECT g FROM Genre g JOIN g.movies m WHERE m.id = :movieId")
    List<Genre> getAllByMovieId(@Param("movieId") Long movieId);
}
