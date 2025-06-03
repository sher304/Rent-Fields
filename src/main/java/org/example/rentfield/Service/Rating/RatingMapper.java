package org.example.rentfield.Service.Rating;

import org.example.rentfield.Model.DTO.ReviewDTO;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.Review;
import org.example.rentfield.Model.User;
import org.springframework.stereotype.Service;

@Service
public class RatingMapper {

    public ReviewDTO map(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setComment(review.getComment());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setField_id(review.getField().getFieldId());
        reviewDTO.setEmail(review.getUser().getEmail());
        return reviewDTO;
    }

    public Review map(ReviewDTO reviewDTO, FootballField field, User user) {
        Review review = new Review();
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        review.setUser(user);
        review.setField(field);
        return review;
    }
}
