package org.senla.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.senla.entity.Member;
import org.senla.entity.Movie;
import org.senla.exceptions.DatabaseGetException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository extends GenericRepository<Member, Long> {

    public MemberRepository() {
        super(Member.class);
    }

    public List<Member> getAllByMovieId(Long movieId) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Member> cq = cb.createQuery(Member.class);
            Root<Member> memberRoot = cq.from(Member.class);
            Join<Member, Movie> movieJoin = memberRoot.join("movies");

            cq.select(memberRoot).where(cb.equal(movieJoin.get("id"), movieId));

            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new DatabaseGetException(Member.class.getSimpleName());
        }
    }
}
