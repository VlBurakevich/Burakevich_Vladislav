package org.senla.mapper;

import lombok.experimental.UtilityClass;
import org.senla.dto.ReviewDto;
import org.senla.entity.Review;

@UtilityClass
public class ReviewMapper {
    public static ReviewDto toDto(Review review) {
        if (review == null) {
            return new ReviewDto();
        }
        ReviewDto dto = new ReviewDto();
        dto.setReviewText(review.getComment());
        dto.setRating(review.getRating());
        return dto;
    }
}
