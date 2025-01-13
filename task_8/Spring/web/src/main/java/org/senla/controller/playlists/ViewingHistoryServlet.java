package org.senla.controller.playlists;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.config.WebConfig;
import org.senla.dto.MoviePreviewDto;
import org.senla.service.ViewingHistoryService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/playlists/viewingHistory")
public class ViewingHistoryServlet extends HttpServlet {
    private ViewingHistoryService viewingHistoryService;

    @Override
    public void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        viewingHistoryService = context.getBean(ViewingHistoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendRedirect(request.getContextPath() + "authorization/login");
            return;
        }

        List<MoviePreviewDto> watchedMovies = viewingHistoryService.getViewingHistoryMovies(username);

        request.setAttribute("username", username);
        request.setAttribute("watchedMovies", watchedMovies);

        request.getRequestDispatcher("/playlists/viewingHistory.jsp").forward(request, response);
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
            case "removeFromWatched":
                viewingHistoryService.removeFromViewingHistory(movieId, username);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid action");
                return;
        }

        response.sendRedirect(request.getContextPath() + "/playlists/viewingHistory");
    }
}
