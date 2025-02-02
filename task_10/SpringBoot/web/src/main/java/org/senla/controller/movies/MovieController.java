package org.senla.controller.movies;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.MemberDto;
import org.senla.dto.MovieAddDto;
import org.senla.dto.MovieInfoDto;
import org.senla.dto.MoviePreviewDto;
import org.senla.enums.GenderType;
import org.senla.enums.MemberType;
import org.senla.service.MovieService;
import org.senla.service.ViewingHistoryService;
import org.senla.service.WatchingListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {
    private MovieService movieService;
    private ViewingHistoryService viewingHistoryService;
    private WatchingListService watchingListService;

    @GetMapping("/list")
    public ResponseEntity<List<MoviePreviewDto>> showMovieList() {
        log.info("Fetching movie list");
        List<MoviePreviewDto> movies = movieService.getMovies();
        log.info("Found {} movies", movies.size());
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/info")
    public ResponseEntity<Object> getMovieInfo(@RequestParam("movieId") long movieId) {
        log.info("Fetching movie info for movieId: {}", movieId);
        try {
            MovieInfoDto movie = movieService.getMovieInfoById(movieId);

            if (movie != null) {
                log.info("Movie found: {}", movie);
                return ResponseEntity.ok(movie);
            } else {
                log.warn("Movie not found for movieId: {}", movieId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
            }
        } catch (Exception e) {
            log.error("Error fetching movie info for movieId: {}", movieId, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Movie ID");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> processAddMovie(@Valid @RequestBody MovieAddDto movieAddDto) {
        try {
            log.info("Adding new movie: {}", movieAddDto);
            List<MemberDto> members = new ArrayList<>();
            if (movieAddDto.getMembers() != null) {
                for (MemberDto memberDto : movieAddDto.getMembers()) {
                    memberDto.setType(MemberType.valueOf(memberDto.getType().toString().toUpperCase()));
                    memberDto.setGender(GenderType.valueOf(memberDto.getGender().toString().toUpperCase()));
                    members.add(memberDto);
                }
            }

            movieAddDto.setMembers(members);
            movieService.addNewMovie(movieAddDto);
            log.info("Movie added successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body("Movie added successfully");
        } catch (Exception e) {
            log.error("Error adding movie: {}", movieAddDto, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteMovie(@RequestParam("movieId") long movieId) {
        log.info("Attempting to delete movie with movieId: {}", movieId);
        try {
            if (movieService.deleteMovie(movieId)) {
                log.info("Movie deleted successfully: {}", movieId);
                return ResponseEntity.ok("Movie deleted successfully");
            } else {
                log.warn("Failed to delete movie with movieId: {}", movieId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete movie");
            }
        } catch (Exception e) {
            log.error("Error deleting movie with movieId: {}", movieId, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Movie ID");
        }
    }

    @PostMapping("/action")
    public ResponseEntity<String> handleAction(
            @RequestParam("action") String action,
            @RequestParam("movieId") long movieId,
            @RequestParam("userId") long userId
    ) {
        log.info("Handling action: {} for movieId: {} and userId: {}", action, movieId, userId);
        try {
            switch (action) {
                case "watch":
                    viewingHistoryService.insert(movieId, userId);
                    log.info("Added to viewing history: movieId: {} userId: {}", movieId, userId);
                    return ResponseEntity.ok("Added to viewing history");
                case "watchLater":
                    watchingListService.insert(movieId, userId);
                    log.info("Added to watch later list: movieId: {} userId: {}", movieId, userId);
                    return ResponseEntity.ok("Added to watch later list");
                default:
                    log.warn("Invalid action: {} for movieId: {} userId: {}", action, movieId, userId);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid action");
            }
        } catch (Exception e) {
            log.error("Error processing action: {} for movieId: {} userId: {}", action, movieId, userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the action: " + e.getMessage());
        }
    }
}
