package org.senla.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.di.container.BeanFactory;
import org.senla.dto.ReviewDto;
import org.senla.service.ReviewService;

import java.io.IOException;

@WebServlet(urlPatterns = "/movies/review")
public class ReviewServlet extends HttpServlet {
    private final BeanFactory beanFactory = new BeanFactory();
    private ReviewService reviewService;

    @Override
    public void init() {
        reviewService = beanFactory.getBean(ReviewService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String movieIdParam = request.getParameter("movieId");
        String reviewText = request.getParameter("review");
        String ratingParam = request.getParameter("rating");

        if (isNullOrEmpty(username) || isNullOrEmpty(movieIdParam) || isNullOrEmpty(ratingParam)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
            return;
        }

        try {
            long movieId = Long.parseLong(movieIdParam);
            int rating = Integer.parseInt(ratingParam);

            if (rating < 0 || rating > 5) {
                throw new IllegalArgumentException("Rating out of range");
            }

            ReviewDto reviewDto = new ReviewDto(movieId, username, reviewText, rating);
            reviewService.saveReview(reviewDto);

            request.getSession().setAttribute("successMessage", "Review saved successfully");
            response.sendRedirect(request.getContextPath() + "/movies/list");

        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
