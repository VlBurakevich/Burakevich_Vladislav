package org.senla.controller.movies;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.senla.dto.MoviePreviewDto;
import org.senla.service.MovieService;
import org.senla.service.ViewingHistoryService;
import org.senla.service.WatchingListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/movies")
public class MovieListController {
    private MovieService movieService;
    private ViewingHistoryService viewingHistoryService;
    private WatchingListService watchingListService;

    @GetMapping("/list")
    public String showMovieList(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/authorization/login";
        }

        List<MoviePreviewDto> movies = movieService.getMovies();
        model.addAttribute("username", username);
        model.addAttribute("movies", movies);

        return "movies/list";
    }

    @PostMapping("/list")
    public String handleAction(
            @RequestParam("action") String action,
            @RequestParam("movieId") Long movieId,
            @RequestParam("username") String username,
            Model model
    ) {
        try {
            switch (action) {
                case "watch":
                    viewingHistoryService.insert(movieId, username);
                    break;
                case "watchLater":
                    watchingListService.insert(movieId, username);
                    break;
                default:
                    model.addAttribute("error", "Invalid action");
                    break;
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error processing the action: " + e.getMessage());
        }

        return "redirect:/movies/list";
    }
}
