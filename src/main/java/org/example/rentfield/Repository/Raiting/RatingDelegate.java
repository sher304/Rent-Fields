package org.example.rentfield.Repository.Raiting;

import org.example.rentfield.Model.Payment;
import org.example.rentfield.Model.Review;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class RatingDelegate {

    Set<Review> ratings;

    public RatingDelegate(Set<Review> ratings) {
        this.ratings = ratings;
    }
}
