package org.example.rentfield.Controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.rentfield.Model.DTO.UserRequestDTO;
import org.example.rentfield.Repository.RegistrationRepository;
import org.example.rentfield.Service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/api/v1/registration")
    public ResponseEntity<Object> userRegistration(@Valid @RequestBody UserRequestDTO userDTO,
                                                   BindingResult result) {

        if (result.hasErrors()) {
            System.out.println("Validation Error " +  result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        try {
            registrationService.registerUser(userDTO);
            return ResponseEntity.ok("User registered");
        } catch (ConstraintViolationException e) {
            List<String> errors = e.getConstraintViolations()
                    .stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .toList();

            return ResponseEntity.badRequest().body(errors);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
