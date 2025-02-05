package org.senla.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.senla.exceptions.DatabaseGetException;
import org.senla.exceptions.DatabaseSaveException;
import org.senla.exceptions.DatabaseUpdateException;
import org.senla.mapper.GenreMapper;
import org.senla.mapper.MemberMapper;
import org.senla.mapper.ReviewMapper;
import org.senla.repository.GenreRepository;
import org.senla.repository.MemberRepository;
import org.senla.repository.MovieRepository;
import org.senla.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MovieService {
    private MovieRepository movieRepository;
    private ReviewRepository reviewRepository;
    private GenreRepository genreRepository;
    private MemberRepository memberRepository;

    public List<MoviePreviewDto> getMovies() {
        return movieRepository.findLimitedRandom(20).stream()
                .map(movie -> new MoviePreviewDto(movie.getId(), movie.getTitle()))
                .toList();
    }

    public MovieInfoDto getMovieInfoById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            throw new DatabaseGetException(Movie.class.getSimpleName());
        }

        List<ReviewDto> reviewDtos = reviewRepository.getAllByMovieId(movieId).stream()
                .map(ReviewMapper::toDto)
                .toList();

        List<GenreDto> genreDtos = genreRepository.getAllByMovieId(movieId).stream()
                .map(GenreMapper::toDto)
                .toList();

        List<MemberDto> memberDtos = memberRepository.getAllByMovieId(movieId).stream()
                .map(MemberMapper::toDto)
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

    @Transactional
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
                    Member member = MemberMapper.toEntity(memberDto);
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
            throw new DatabaseSaveException(Movie.class.getSimpleName());
        }
    }

    @Transactional
    public boolean deleteMovie(Long movieId) {
        try {
            movieRepository.deleteById(movieId);
            return true;
        } catch (Exception e) {
            throw new DatabaseException("ErrorDeleting movie and related records", e);
        }
    }
}
