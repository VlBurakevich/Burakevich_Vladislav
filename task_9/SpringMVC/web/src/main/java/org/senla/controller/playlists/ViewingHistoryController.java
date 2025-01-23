package org.senla.controller.playlists;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.senla.dto.MoviePreviewDto;
import org.senla.service.ViewingHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/playlists")
public class ViewingHistoryController {
    private ViewingHistoryService viewingHistoryService;

    @GetMapping("/viewingHistory")
    public String getViewingHistory(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/authorization/login";
        }

        List<MoviePreviewDto> watchedMovies = viewingHistoryService.getViewingHistoryMovies(username);
        model.addAttribute("username", username);
        model.addAttribute("watchedMovies", watchedMovies);

        return "/playlists/viewingHistory";
    }

    @PostMapping("/viewingHistory")
    public String postViewingHistory(
            @RequestParam("action") String action,
            @RequestParam("movieId") Long movieId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/authorization/login";
        }

        if (action.equals("removeFromWatched")) {
            viewingHistoryService.removeFromViewingHistory(movieId, username);
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid action");
            return "redirect:/playlists/viewingHistory";
        }

        return "redirect:/playlists/viewingHistory";
    }
}
