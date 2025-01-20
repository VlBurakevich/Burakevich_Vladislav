package org.senla.controller.playlists;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.senla.dto.MoviePreviewDto;
import org.senla.service.WatchingListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class WatchLaterController {
    private WatchingListService watchingListService;

    @GetMapping("/playlists/watchLater")
    public String getWatchLaterMovies(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/authorization/login";
        }

        List<MoviePreviewDto> watchLaterMovies = watchingListService.getWatchLaterMovies(username);
        model.addAttribute("username", username);
        model.addAttribute("watchLaterMovies", watchLaterMovies);

        return "/playlists/watchLater";
    }

    @PostMapping("/playlists/watchLater")
    public String postWatchLaterAction(
            @RequestParam("action") String action,
            @RequestParam("movieId") Long movieId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/authorization/login";
        }

        if (action.equals("removeFromWatchLater")) {
            watchingListService.removeFromWatchLater(movieId, username);
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid action");
            return "redirect:/playlists/watchLater";
        }

        return "redirect:/playlists/watchLater";
    }
}
