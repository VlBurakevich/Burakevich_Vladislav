package org.senla.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.di.container.BeanFactory;
import org.senla.dto.MovieAddDto;
import org.senla.service.MovieService;

import java.io.IOException;

@WebServlet(urlPatterns = "/movies/add")
public class MovieAddServlet extends HttpServlet {
    private final BeanFactory beanFactory = new  BeanFactory();
    private MovieService movieService;

    @Override
    public void init() {
        movieService = beanFactory.getBean(MovieService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/movies/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            MovieAddDto movieAddDto = new MovieAddDto(
                    request.getParameter("title"),
                    request.getParameter("description"),
                    request.getParameter("duration"),
                    request.getParameter("releaseDate"),
                    request.getParameter("genreNames"),
                    request.getParameterValues("memberFirstName"),
                    request.getParameterValues("memberLastName"),
                    request.getParameterValues("memberNationality"),
                    request.getParameterValues("memberType"),
                    request.getParameterValues("memberGender")
            );

            movieService.addNewMovie(movieAddDto);

            response.sendRedirect(request.getContextPath() + "/movies/list");


        } catch (RuntimeException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
