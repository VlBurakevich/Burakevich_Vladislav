package org.senla.service;

import lombok.AllArgsConstructor;
import org.senla.dto.ReviewDto;
import org.senla.entity.Review;
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
            review.setMovie(movieRepository.getReferenceById(reviewDto.getMovieId()));
            review.setUser(userRepository.getReferenceByUsername(reviewDto.getUsername()));
            review.setRating(reviewDto.getRating());
            review.setComment(reviewDto.getReviewText());
            reviewRepository.save(review);
        } catch (RuntimeException e) {
            throw new DatabaseSaveException(Review.class.getSimpleName());
        }
    }
}
