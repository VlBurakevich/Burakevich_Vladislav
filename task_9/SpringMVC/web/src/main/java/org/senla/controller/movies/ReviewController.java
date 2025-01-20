package org.senla.controller.movies;

import lombok.AllArgsConstructor;
import org.senla.dto.ReviewDto;
import org.senla.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping("/movies/review")
    public String submitReview(
            @RequestParam("username") String username,
            @RequestParam("movieId") Long movieId,
            @RequestParam("review") String reviewText,
            @RequestParam("rating") Integer rating,
            RedirectAttributes redirectAttributes) {
        if (isNullOrEmpty(username) || movieId == null || isNullOrEmpty(rating.toString())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Missing required fields");
            return "redirect:/movies/list";
        }

        try {
            if (rating < 0 || rating > 5) {
                throw new IllegalArgumentException("Rating out of range");
            }

            ReviewDto reviewDto = new ReviewDto(movieId, username, reviewText, rating);
            reviewService.saveReview(reviewDto);

            redirectAttributes.addFlashAttribute("successMessage", "Review saved successfully");
            return "redirect:/movies/list";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid input");
            return "redirect:/movies/list";
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
