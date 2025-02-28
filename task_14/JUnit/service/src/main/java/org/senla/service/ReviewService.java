package org.senla.service;

import lombok.AllArgsConstructor;
import org.senla.dto.ReviewDto;
import org.senla.entity.Movie;
import org.senla.entity.Review;
import org.senla.entity.User;
import org.senla.exceptions.DatabaseSaveException;
import org.senla.repository.MovieRepository;
import org.senla.repository.ReviewRepository;
import org.senla.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class ReviewService {

    private MovieRepository movieRepository;

    private UserRepository userRepository;

    private ReviewRepository reviewRepository;

    @Transactional
    public void saveReview(ReviewDto reviewDto) {
        try {
            Review review = new Review();
            Movie movie = movieRepository.findById(reviewDto.getMovieId())
                    .orElseThrow(() -> new DatabaseSaveException("Invalid movie"));
            User user = userRepository.findByUsername(reviewDto.getUsername())
                    .orElseThrow(() -> new DatabaseSaveException("Invalid user"));

            review.setMovie(movie);
            review.setUser(user);
            review.setRating(reviewDto.getRating());
            review.setComment(reviewDto.getReviewText());
            reviewRepository.save(review);
        } catch (RuntimeException e) {
            throw new DatabaseSaveException(Review.class.getSimpleName());
        }
    }
}
