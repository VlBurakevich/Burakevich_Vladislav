package org.senla.repository;

import org.senla.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT mem FROM Member mem JOIN mem.movies m WHERE m.id = :movieId")
    List<Member> getAllByMovieId(@Param("movieId") Long movieId);
}
