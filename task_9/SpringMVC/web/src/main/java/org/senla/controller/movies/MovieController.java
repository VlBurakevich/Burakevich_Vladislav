package org.senla.controller.movies;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.MemberDto;
import org.senla.dto.MovieAddDto;
import org.senla.dto.MovieInfoDto;
import org.senla.dto.MoviePreviewDto;
import org.senla.enums.GenderType;
import org.senla.enums.MemberType;
import org.senla.service.MovieService;
import org.senla.service.ViewingHistoryService;
import org.senla.service.WatchingListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private MovieService movieService;
    private ViewingHistoryService viewingHistoryService;
    private WatchingListService watchingListService;


    @GetMapping("/add")
    public String showMoviePage(Model model) {
        model.addAttribute("movieAddDto", new MovieAddDto());
        return "movies/add";
    }

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

    @GetMapping("/info")
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

    @PostMapping("/add")
    public String processAddMovie(
            @Valid @ModelAttribute("movieAddDto") MovieAddDto movieAddDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "movies/add";
        }

        try {
            List<MemberDto> members = new ArrayList<>();
            if (movieAddDto.getMembers() != null) {
                for (int i = 0; i < movieAddDto.getMembers().size(); i++) {
                    MemberDto memberDto = movieAddDto.getMembers().get(i);

                    memberDto.setType(MemberType.valueOf(movieAddDto.getMembers().get(i).getType().toString().toUpperCase()));
                    memberDto.setGender(GenderType.valueOf(movieAddDto.getMembers().get(i).getGender().toString().toUpperCase()));

                    members.add(memberDto);
                }
            }

            movieAddDto.setMembers(members);
            movieService.addNewMovie(movieAddDto);

            return "redirect:/movies/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "movies/add";
        }
    }

    @PostMapping("/delete")
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
