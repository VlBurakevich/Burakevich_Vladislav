package org.senla.repository;

import org.senla.entity.Genre;
import org.senla.entity.Member;
import org.senla.entity.Movie;
import org.senla.exceptions.DatabaseDeleteException;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository extends GenericRepository<Movie, Long> {

    public MovieRepository() {
        super(Movie.class);
    }

    @Override
    public void deleteById(Long id) {
        try {
            entityManager.getTransaction().begin();
            Movie movie = getById(id);

            for (Genre genre : movie.getGenres()) {
                if (genre.getMovies().size() == 1) {
                    entityManager.remove(genre);
                }
            }

            for (Member member : movie.getMembers()) {
                if (member.getMovies().size() == 1) {
                    entityManager.remove(member);
                }
            }

            entityManager.remove(movie);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseDeleteException(Movie.class.getName());
        }
    }
}
