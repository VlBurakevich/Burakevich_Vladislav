package org.senla.controller.playlists;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.config.WebConfig;
import org.senla.dto.MoviePreviewDto;
import org.senla.service.WatchingListService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/playlists/watchLater")
public class WatchLaterServlet extends HttpServlet {
    private WatchingListService watchingListService;

    @Override
    public void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        watchingListService = context.getBean(WatchingListService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<MoviePreviewDto> watchLaterMovies = watchingListService.getWatchLaterMovies(username);

        request.setAttribute("username", username);
        request.setAttribute("watchLaterMovies", watchLaterMovies);

        request.getRequestDispatcher("/playlists/watchLater.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        String username = request.getParameter("username");

        if (username == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        switch (action) {
            case "removeFromWatchLater":
                watchingListService.removeFromWatchLater(movieId, username);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid action");
                return;
        }

        response.sendRedirect(request.getContextPath() + "/playlists/watchLater");
    }
}
