package org.senla.controller.playlists;

import lombok.AllArgsConstructor;
import org.senla.dto.MoviePreviewDto;
import org.senla.service.AuthService;
import org.senla.service.ViewingHistoryService;
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
public class ViewingHistoryController {
    private ViewingHistoryService viewingHistoryService;
    private AuthService authService;

    @GetMapping("/viewingHistory")
    public ResponseEntity<Object> getViewingHistory() {
        Long userId = authService.getAuthenticatedUserId();
        List<MoviePreviewDto> watchedMovies = viewingHistoryService.getViewingHistoryMovies(userId);
        return ResponseEntity.ok(watchedMovies);
    }

    @PostMapping("/viewingHistory")
    public ResponseEntity<String> postViewingHistory(
            @RequestParam("action") String action,
            @RequestParam("movieId") long movieId
    ) {
        try {
            Long userId = authService.getAuthenticatedUserId();

            if (action.equals("removeFromWatched")) {
                viewingHistoryService.removeFromViewingHistory(movieId, userId);
                return ResponseEntity.ok("Movie removed from viewing history");
            } else {
                return ResponseEntity.status(400).body("Invalid action");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing action: " + e.getMessage());
        }
    }
}
