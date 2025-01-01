package org.senla.service;

import org.senla.dao.MovieDao;
import org.senla.dao.ReviewDao;
import org.senla.dao.UserDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.dto.ReviewDto;
import org.senla.entity.Review;

import java.util.List;

@Component
public class ReviewService {

    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MovieDao movieDao;

    public Review getById(Long id) {
        return reviewDao.getById(id);
    }

    public List<Review> getAll() {
        return reviewDao.getAll();
    }

    public void insert(Review review) {
        reviewDao.insert(review);
    }

    public void update(Review review) {
        reviewDao.update(review);
    }

    public void delete(Long id) {
        reviewDao.delete(id);
    }

    public void saveReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setUser(userDao.getByUsername(reviewDto.getUsername()));
        review.setMovie(movieDao.getById(reviewDto.getMovieId()));
        review.setComment(reviewDto.getReviewText());
        review.setRating(reviewDto.getRating());

        System.out.println("Review: " + review);

        reviewDao.insert(review);
    }
}
