package org.example.rentfield.Controller;

import org.example.rentfield.CustomException.FieldNotFoundException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.DTO.ReviewDTO;
import org.example.rentfield.Service.Rating.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateController {

    private final RatingService ratingService;

    public RateController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/api/v1/rate")
    public ResponseEntity<?> writeNewRating(@Valid @RequestBody ReviewDTO reviewDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            ratingService.writeNewReview(reviewDTO);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FieldNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
