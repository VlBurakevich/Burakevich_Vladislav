package org.senla.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.ReviewDto;
import org.senla.entity.Review;
import org.senla.repository.MovieRepository;
import org.senla.repository.ReviewRepository;
import org.senla.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewService {

    private MovieRepository movieRepository;

    private UserRepository userRepository;

    private ReviewRepository reviewRepository;

    public Review getById(Long id) {
        return reviewRepository.getById(id);
    }

    public List<Review> getAll() {
        return reviewRepository.fetchLimitedRandom(20);
    }

    public void insert(Review review) {
        reviewRepository.save(review);
    }

    public void update(Review review) {
        reviewRepository.update(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }


    public void saveReview(ReviewDto reviewDto) {
        log.info("Saving review for movie ID: {}, by user: {}", reviewDto.getMovieId(), reviewDto.getUsername());
        try {
            Review review = new Review();
            review.setMovie(movieRepository.getById(reviewDto.getMovieId()));
            review.setUser(userRepository.getByUsername(reviewDto.getUsername()));
            review.setRating(reviewDto.getRating());
            review.setComment(reviewDto.getReviewText());
            reviewRepository.save(review);
            log.info("Review successfully saved for movie ID: {}, by user: {}", reviewDto.getMovieId(), reviewDto.getUsername());
        } catch (Exception e) {
            log.error("Error saving review for movie ID: {}, by user: {}", reviewDto.getMovieId(), reviewDto.getUsername(), e);
            throw e;
        }
    }

}
