package org.senla.service;

import org.senla.dao.GenreDao;
import org.senla.dao.MemberDao;
import org.senla.dao.MovieDao;
import org.senla.dao.ReviewDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.dto.GenreDto;
import org.senla.dto.MemberDto;
import org.senla.dto.MovieAddDto;
import org.senla.dto.MovieInfoDto;
import org.senla.dto.MoviePreviewDto;
import org.senla.dto.ReviewDto;
import org.senla.entity.Genre;
import org.senla.entity.Member;
import org.senla.entity.Movie;
import org.senla.enums.GenderType;
import org.senla.enums.MemberType;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.entityExceptions.MovieNotFoundException;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieService {

    @Autowired
    private MovieDao movieDao;
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private MemberDao memberDao;

    public Movie getById(Long id) {
        return movieDao.getById(id);
    }

    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    public void insert(Movie movie) {
        movieDao.insert(movie);
    }

    public void update(Movie movie) {
        movieDao.update(movie);
    }

    public void delete(Long id) {
        movieDao.delete(id);
    }

    public List<MoviePreviewDto> getMovies() {
        List<Movie> movies = movieDao.getRandomMovies(20);
        List<MoviePreviewDto> movieDtos = new ArrayList<>();
        for (Movie movie : movies) {
            movieDtos.add(new MoviePreviewDto(movie.getId(), movie.getTitle()));
        }

        return movieDtos;
    }

    public MovieInfoDto getMovieInfoById(Long movieId) {
        Movie movie = movieDao.getById(movieId);
        if (movie == null) {
            throw new MovieNotFoundException(movieId);
        }

        List<ReviewDto> reviewDtos = reviewDao.getAllByMovieId(movieId).stream()
                .map(ReviewDto::new)
                .toList();

        List<GenreDto> genreDtos = genreDao.getAllByMovieId(movieId).stream()
                .map(GenreDto::new)
                .toList();

        List<MemberDto> memberDtos = memberDao.getAllByMovieId(movieId).stream()
                .map(MemberDto::new)
                .toList();

        return MovieInfoDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .duration(movie.getDuration())
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
            movie.setDuration(duration);
            movie.setReleaseDate(releaseDate);
            movie.setGenres(genres);
            movie.setMembers(members);

            movieDao.addNewMovie(movie);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteMovie(long movieId) {
        try {
            movieDao.delete(movieId);
            return true;
        } catch (Exception e) {
            throw new DatabaseException("ErrorDeleting movie and related records", e);
        }
    }
}
