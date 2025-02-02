package org.senla.controller.playlists;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.MoviePreviewDto;
import org.senla.service.ViewingHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/playlists")
public class ViewingHistoryController {
    private ViewingHistoryService viewingHistoryService;

    @GetMapping("/viewingHistory")
    public ResponseEntity<Object> getViewingHistory(@RequestParam("userId") long userId) {
        log.info("Fetching viewing history for userId: {}", userId);
        List<MoviePreviewDto> watchedMovies = viewingHistoryService.getViewingHistoryMovies(userId);
        log.info("Found {} movies in viewing history for userId: {}", watchedMovies.size(), userId);
        return ResponseEntity.ok(watchedMovies);
    }

    @PostMapping("/viewingHistory")
    public ResponseEntity<String> postViewingHistory(
            @RequestParam("action") String action,
            @RequestParam("movieId") long movieId,
            @RequestParam("userId") long userId
    ) {
        log.info("Processing action: {} for movieId: {} and userId: {}", action, movieId, userId);
        try {
            if (action.equals("removeFromWatched")) {
                viewingHistoryService.removeFromViewingHistory(movieId, userId);
                log.info("Movie removed from viewing history: movieId: {} userId: {}", movieId, userId);
                return ResponseEntity.ok("Movie removed from viewing history");
            } else {
                log.warn("Invalid action: {} for movieId: {} userId: {}", action, movieId, userId);
                return ResponseEntity.status(400).body("Invalid action");
            }
        } catch (Exception e) {
            log.error("Error processing action: {} for movieId: {} userId: {}", action, movieId, userId, e);
            return ResponseEntity.status(500).body("Error processing action: " + e.getMessage());
        }
    }
}
