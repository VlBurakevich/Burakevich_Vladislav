package org.senla.controller.movies;

import lombok.AllArgsConstructor;
import org.senla.dto.MovieInfoDto;
import org.senla.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class MovieInfoController {
    private MovieService movieService;

    @GetMapping("/movies/info")
    public String getMovieInfo(@RequestParam("movieId") long movieId, Model model) {
        try {
            MovieInfoDto movie = movieService.getMovieInfoById(movieId);

            if (movie != null) {
                model.addAttribute("movie", movie);
                return "movies/info";
            } else {
                return "redirect:/movies/info";
            }
        } catch (NumberFormatException | NullPointerException e) {
            model.addAttribute("error", "Invalid or missing Movie ID");
            return "movies/list";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while retrieving the movie info");
            return "movies/list";
        }
    }
}
