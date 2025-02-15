package org.senla.controller.playlists;

import lombok.AllArgsConstructor;
import org.senla.dto.MoviePreviewDto;
import org.senla.service.AuthService;
import org.senla.service.WatchingListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/playlists")
public class WatchLaterController {
    private final AuthService authService;
    private WatchingListService watchingListService;

    @GetMapping("/watchLater")
    public ResponseEntity<Object> getWatchLaterMovies() {
        Long userId = authService.getAuthenticatedUserId();
        List<MoviePreviewDto> watchLaterMovies = watchingListService.getWatchLaterMovies(userId);
        return ResponseEntity.ok(watchLaterMovies);
    }

    @PostMapping("/watchLater")
    public ResponseEntity<String> postWatchLaterAction(
            @RequestParam(name = "action") String action,
            @RequestParam(name = "movieId") long movieId
    ) {
        try {
            Long userId = authService.getAuthenticatedUserId();

            if (action.equals("removeFromWatchLater")) {
                watchingListService.removeFromWatchLater(movieId, userId);
                return ResponseEntity.ok("Movie removed from watch later");
            } else {
                return ResponseEntity.status(400).body("Invalid action");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing action: " + e.getMessage());
        }
    }
}
