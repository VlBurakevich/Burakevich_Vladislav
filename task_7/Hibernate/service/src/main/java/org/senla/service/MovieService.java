package org.senla.service;

import lombok.AllArgsConstructor;
import org.senla.dto.GenreDto;
import org.senla.dto.MemberDto;
import org.senla.dto.MovieAddDto;
import org.senla.dto.MovieInfoDto;
import org.senla.dto.MoviePreviewDto;
import org.senla.dto.ReviewDto;
import org.senla.entity.Genre;
import org.senla.entity.Member;
import org.senla.entity.Movie;
import org.senla.exceptions.DatabaseException;
import org.senla.repository.GenreRepository;
import org.senla.repository.MemberRepository;
import org.senla.repository.MovieRepository;
import org.senla.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
    private MovieRepository movieRepository;

    private ReviewRepository reviewRepository;

    private GenreRepository genreRepository;

    private MemberRepository memberRepository;

    public Movie getById(Long id) {
        return movieRepository.getById(id);
    }

    public List<Movie> getAll() {
        return movieRepository.fetchLimitedRandom(20);
    }

    public void insert(Movie movie) {
        movieRepository.save(movie);
    }

    public void update(Movie movie) {
        movieRepository.update(movie);
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    public List<MoviePreviewDto> getMovies() {
        List<Movie> movies = movieRepository.fetchLimitedRandom(20);
        List<MoviePreviewDto> movieDtos = new ArrayList<>();
        for (Movie movie : movies) {
            movieDtos.add(new MoviePreviewDto(movie.getId(), movie.getTitle()));
        }

        return movieDtos;
    }

    public MovieInfoDto getMovieInfoById(Long movieId) {
        Movie movie = movieRepository.getById(movieId);
        if (movie == null) {
            throw new RuntimeException("Movie not found");
        }

        List<ReviewDto> reviewDtos = reviewRepository.getAllByMovieId(movieId).stream()
                .map(ReviewDto::new)
                .toList();

        List<GenreDto> genreDtos = genreRepository.getAllByMovieId(movieId).stream()
                .map(GenreDto::new)
                .toList();

        List<MemberDto> memberDtos = memberRepository.getAllByMovieId(movieId).stream()
                .map(MemberDto::new)
                .toList();

        return MovieInfoDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .duration(Duration.ofSeconds(movie.getDuration()))
                .releaseDate(movie.getReleaseDate())
                .reviews(reviewDtos)
                .genres(genreDtos)
                .members(memberDtos)
                .build();

    }

    public List<MoviePreviewDto> convertMoviesToPreviewDto(List<Movie> movies) {
        List<MoviePreviewDto> moviePreviewDtos = new ArrayList<>();
        for (Movie movie : movies) {
            MoviePreviewDto dto = new MoviePreviewDto(movie.getId(), movie.getTitle());
            moviePreviewDtos.add(dto);
        }
        return moviePreviewDtos;
    }

    public void addNewMovie(MovieAddDto movieAddDto) {
        try {
            Duration duration = Duration.parse(movieAddDto.getDuration());
            Date releaseDate = Date.valueOf(movieAddDto.getReleaseDate());

            List<Genre> genres = new ArrayList<>();
            if (movieAddDto.getGenreNames() != null && !movieAddDto.getGenreNames().isEmpty()) {
                for (String genreName : movieAddDto.getGenreNames().split(",")) {
                    Genre genre = new Genre();
                    genre.setName(genreName.trim());
                    genres.add(genre);
                }
            }


            List<Member> members = new ArrayList<>();
            if (movieAddDto.getMembers() != null) {
                for (MemberDto memberDto : movieAddDto.getMembers()) {
                    Member member = new Member();
                    member.setId(memberDto.getId());
                    member.setFirstName(memberDto.getFirstName());
                    member.setLastName(memberDto.getLastName());
                    member.setNationality(memberDto.getNationality());
                    member.setType(memberDto.getType());
                    member.setGender(memberDto.getGender());

                    members.add(member);
                }
            }

            Movie movie = new Movie();
            movie.setTitle(movieAddDto.getTitle());
            movie.setDescription(movieAddDto.getDescription());
            movie.setDuration((int) duration.toSeconds());
            movie.setReleaseDate(releaseDate);
            movie.setGenres(genres);
            movie.setMembers(members);

            movieRepository.save(movie);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteMovie(long movieId) {
        try {
            movieRepository.deleteById(movieId);
            return true;
        } catch (Exception e) {
            throw new DatabaseException("ErrorDeleting movie and related records", e);
        }
    }
}
