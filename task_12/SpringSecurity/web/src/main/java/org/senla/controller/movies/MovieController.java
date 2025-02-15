package org.senla.controller.movies;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.MemberDto;
import org.senla.dto.MovieAddDto;
import org.senla.dto.MovieInfoDto;
import org.senla.dto.MoviePreviewDto;
import org.senla.enums.GenderType;
import org.senla.enums.MemberType;
import org.senla.service.AuthService;
import org.senla.service.MovieService;
import org.senla.service.ViewingHistoryService;
import org.senla.service.WatchingListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {
    private MovieService movieService;
    private ViewingHistoryService viewingHistoryService;
    private WatchingListService watchingListService;
    private AuthService authService;

    @GetMapping("/list")
    public ResponseEntity<List<MoviePreviewDto>> showMovieList() {
        List<MoviePreviewDto> movies = movieService.getMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/info")
    public ResponseEntity<Object> getMovieInfo(@RequestParam("movieId") long movieId) {
        try {
            MovieInfoDto movie = movieService.getMovieInfoById(movieId);

            if (movie != null) {
                return ResponseEntity.ok(movie);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Movie ID");
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> processAddMovie(@Valid @RequestBody MovieAddDto movieAddDto) {
        try {
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

            return ResponseEntity.status(HttpStatus.CREATED).body("Movie added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteMovie(@RequestParam("movieId") long movieId) {
        try {
            if (movieService.deleteMovie(movieId)) {
                return ResponseEntity.ok("Movie deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete movie");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Movie ID");
        }
    }

    @PostMapping("/action")
    public ResponseEntity<String> handleAction(
            @RequestParam("action") String action,
            @RequestParam("movieId") long movieId
    ) {
        try {
            Long userId = authService.getAuthenticatedUserId();

            switch (action) {
                case "watch":
                    viewingHistoryService.insert(movieId, userId);
                    return ResponseEntity.ok("Added to viewing history");
                case "watchLater":
                    watchingListService.insert(movieId, userId);
                    return ResponseEntity.ok("Added to watch later list");
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid action");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the action: " + e.getMessage());
        }
    }
}
