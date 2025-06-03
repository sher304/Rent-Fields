package org.example.rentfield.Service.Rating;

import org.example.rentfield.CustomException.FieldNotFoundException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.DTO.ReviewDTO;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.Review;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.Field.FieldRepository;
import org.example.rentfield.Repository.Raiting.RatingRepository;
import org.example.rentfield.Repository.User.RegistrationRepository;
import org.example.rentfield.Service.Registatoin.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final RegistrationRepository registrationRepository;
    private final FieldRepository fieldRepository;

    public RatingService(RatingRepository ratingRepository,
                         RatingMapper ratingMapper,
                         RegistrationRepository registrationRepository,
                         FieldRepository fieldRepository, UserMapper userMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
        this.registrationRepository = registrationRepository;
        this.fieldRepository = fieldRepository;
    }


    public void writeNewReview(ReviewDTO reviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = registrationRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Authorise to write review!"));
        FootballField field = fieldRepository.findById(reviewDTO.getField_id()).orElseThrow(() -> new FieldNotFoundException("Field not found"));

        Review review = ratingMapper.map(reviewDTO, field, user);
        ratingRepository.save(review);
    }

}
