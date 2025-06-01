package org.example.rentfield.Service;

import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.RegistrationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final RegistrationRepository registrationRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(RegistrationRepository registrationRepository,
                        PasswordEncoder passwordEncoder) {
        this.registrationRepository = registrationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return registrationRepository.findByEmail(email);
    }

    public boolean validateCredentials(String email, String passwordRaw) {
        return registrationRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(passwordRaw, user.getPassword()))
                .orElse(false);
    }
}
