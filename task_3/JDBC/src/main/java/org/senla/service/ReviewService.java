package org.senla.service;

import org.senla.dao.ReviewDao;
import org.senla.entity.Review;

import java.util.List;

public class ReviewService {
    private final ReviewDao reviewDao;

    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

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
}
