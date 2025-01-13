package org.senla.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.di.container.BeanFactory;
import org.senla.service.MovieService;

import java.io.IOException;

@WebServlet(urlPatterns = "/movies/delete")
public class MovieDeleteServlet extends HttpServlet {
    private final BeanFactory beanFactory = new BeanFactory();

    private MovieService movieService;

    @Override
    public void init() throws ServletException {
        movieService = beanFactory.getBean(MovieService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movieIdParam = request.getParameter("movieId");

        try {
            if (movieIdParam == null || movieIdParam.isEmpty()) {
                throw new IllegalArgumentException("Movie ID is required");
            }

            long movieId = Long.parseLong(movieIdParam);

            if (movieService.deleteMovie(movieId)) {
                response.sendRedirect(request.getContextPath() + "/movies");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete movie");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Movie ID");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the movie");
        }
    }
}
