package org.example.rentfield.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.example.rentfield.Model.DTO.UserMapper;
import org.example.rentfield.Model.DTO.UserRequestDTO;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.RegistrationRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.Set;

@Service
public class RegistrationService {

    private final UserMapper userMapper;
    private RegistrationRepository registrationRepository;
    private final Validator validator;

    public RegistrationService(RegistrationRepository registrationRepository,
                               Validator validator, UserMapper userMapper) {
        this.registrationRepository = registrationRepository;
        this.validator = validator;
        this.userMapper = userMapper;
    }

    public void registerUser(UserRequestDTO userRequestDTO) throws ConstraintViolationException, DataIntegrityViolationException {
        User user = userMapper.map(userRequestDTO);

        System.out.println("Checking phone: " + user.getPhoneNumber());
        System.out.println("Exists: " + registrationRepository.existsByPhoneNumber(user.getPhoneNumber()));

        if (registrationRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        if (registrationRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("Phone number already in use");
        }

        Set<ConstraintViolation<User>> errors = validator.validate(user);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }

        try {
            registrationRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage());
        }
    }
}
