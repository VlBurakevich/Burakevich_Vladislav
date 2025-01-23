package org.senla.controller.movies;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.ReviewDto;
import org.senla.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@AllArgsConstructor
@RequestMapping("/movies")
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping("/review")
    public String submitReview(
            @Valid @ModelAttribute("reviewDto") ReviewDto reviewDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid review input");
            return "redirect:/movies/list";
        }

        try {
            reviewService.saveReview(reviewDto);
            redirectAttributes.addFlashAttribute("successMessage", "Review saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving the review");
        }

        return "redirect:/movies/list";
    }
}
