package org.senla.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.di.container.BeanFactory;
import org.senla.dto.MovieInfoDto;
import org.senla.service.MovieService;

import java.io.IOException;

@WebServlet(urlPatterns = "/movies/info")
public class MovieInfoServlet extends HttpServlet {
    private final BeanFactory beanFactory = new BeanFactory();
    private MovieService movieService;

    @Override
    public void init() {
        movieService = beanFactory.getBean(MovieService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long movieId = Long.parseLong(request.getParameter("movieId"));
            MovieInfoDto movie = movieService.getMovieInfoById(movieId);

            if (movie != null) {
                request.setAttribute("movie", movie);
                request.getRequestDispatcher("/movies/info.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/movies/list");
            }
        } catch (NumberFormatException | NullPointerException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing Movie ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the movie info");
        }
    }
}
