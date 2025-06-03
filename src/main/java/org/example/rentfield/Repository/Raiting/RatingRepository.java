package org.example.rentfield.Repository.Raiting;


import org.example.rentfield.Model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatingRepository extends CrudRepository<Review, Long> {
    List<Review> findAllByField_FieldId(int fieldId);

}
