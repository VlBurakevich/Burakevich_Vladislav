package org.senla.controller.movies;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.ReviewDto;
import org.senla.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
@RequestMapping("/api/movies")
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping("/review")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> submitReview(@Valid @RequestBody ReviewDto reviewDto) {
        try {
            reviewService.saveReview(reviewDto);
            return ResponseEntity.ok("Review saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving review");
        }
    }
}
