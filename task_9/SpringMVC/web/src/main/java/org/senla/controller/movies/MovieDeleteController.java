package org.senla.controller.movies;

import lombok.AllArgsConstructor;
import org.senla.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@AllArgsConstructor
public class MovieDeleteController {
    private MovieService movieService;

    @PostMapping("/movies/delete")
    public String deleteMovie(@RequestParam("movieId") long movieId, Model model) {
        try {
            if (movieService.deleteMovie(movieId)) {
                return "redirect:/movies/list";
            }
            model.addAttribute("error", "Failed to delete movie");

        } catch (Exception e) {
            model.addAttribute("error", "Invalid Movie ID");
        }

        return "/movies/list";
    }
}
