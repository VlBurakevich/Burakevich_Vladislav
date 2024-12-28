package org.senla.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.di.container.BeanFactory;
import org.senla.dto.MemberDto;
import org.senla.dto.MovieAddDto;
import org.senla.enums.GenderType;
import org.senla.enums.MemberType;
import org.senla.service.MovieService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            String[] firstNames = request.getParameterValues("memberFirstName");
            String[] lastNames = request.getParameterValues("memberLastName");
            String[] nationalities = request.getParameterValues("memberNationality");
            String[] types = request.getParameterValues("memberType");
            String[] genres = request.getParameterValues("memberGenres");

            List<MemberDto> members = new ArrayList<>();
            if (firstNames != null) {
                for (int i = 0; i < firstNames.length; i++) {
                    MemberDto memberDto = new MemberDto();

                    memberDto.setFirstName(firstNames[i]);
                    memberDto.setLastName(lastNames[i]);
                    memberDto.setNationality(nationalities[i]);
                    memberDto.setType(MemberType.valueOf(types[i].toUpperCase()));
                    memberDto.setGender(GenderType.valueOf(genres[i].toUpperCase()));

                    members.add(memberDto);
                }
            }


            MovieAddDto movieAddDto = MovieAddDto.builder()
                    .title(request.getParameter("title"))
                    .description(request.getParameter("description"))
                    .duration(request.getParameter("duration"))
                    .releaseDate(request.getParameter("releaseDate"))
                    .genreNames(request.getParameter("genreNames"))
                    .members(members)
                    .build();


            movieService.addNewMovie(movieAddDto);

            response.sendRedirect(request.getContextPath() + "/movies/list");


        } catch (RuntimeException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
