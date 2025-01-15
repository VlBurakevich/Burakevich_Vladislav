package org.senla.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.di.container.BeanFactory;
import org.senla.dto.MoviePreviewDto;
import org.senla.service.MovieService;
import org.senla.service.ViewingHistoryService;
import org.senla.service.WatchingListService;

import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/movies/list")
public class MovieListServlet extends HttpServlet {
    private final BeanFactory beanFactory = new BeanFactory();
    private MovieService movieService;
    private ViewingHistoryService viewingHistoryService;
    private WatchingListService watchingListService;

    @Override
    public void init() {
        movieService = beanFactory.getBean(MovieService.class);
        viewingHistoryService = beanFactory.getBean(ViewingHistoryService.class);
        watchingListService = beanFactory.getBean(WatchingListService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<MoviePreviewDto> movies = movieService.getMovies();

        request.setAttribute("username", username);
        request.setAttribute("movies", movies);

        request.getRequestDispatcher("/movies/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        String username = request.getParameter("username");

        switch (action) {
            case "watch":
                viewingHistoryService.insert(movieId, username);
                break;
            case "watchLater":
                watchingListService.insert(movieId, username);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid action");
                return;
        }

        response.sendRedirect(request.getContextPath() + "/movies/list");
    }
}
