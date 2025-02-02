package org.senla.controller.movies;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.ReviewDto;
import org.senla.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/api/movies")
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<String> submitReview(@Valid @RequestBody ReviewDto reviewDto) {
        log.info("Received review submission for movie ID: {}", reviewDto.getMovieId());

        try {
            reviewService.saveReview(reviewDto);
            log.info("Review saved successfully for movie ID: {}", reviewDto.getMovieId());
            return ResponseEntity.ok("Review saved successfully");
        } catch (Exception e) {
            log.error("Error saving review for movie ID: {}: {}", reviewDto.getMovieId(), e.getMessage());
            return ResponseEntity.status(500).body("Error saving review");
        }
    }
}
