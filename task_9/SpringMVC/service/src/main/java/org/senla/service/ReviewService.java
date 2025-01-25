package org.senla.service;

import lombok.AllArgsConstructor;
import org.senla.dto.ReviewDto;
import org.senla.entity.Review;
import org.senla.repository.MovieRepository;
import org.senla.repository.ReviewRepository;
import org.senla.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Review review = new Review();
        review.setMovie(movieRepository.getById(reviewDto.getMovieId()));
        review.setUser(userRepository.getByUsername(reviewDto.getUsername()));
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getReviewText());
        reviewRepository.save(review);
    }
}
