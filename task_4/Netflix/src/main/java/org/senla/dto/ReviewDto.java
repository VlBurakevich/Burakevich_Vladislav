package org.senla.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.senla.entity.Review;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    Long movieId;
    String username;
    String reviewText;
    int rating;

    public ReviewDto(Review review) {
        this.reviewText = review.getComment();
        this.rating = review.getRating();
    }
}
