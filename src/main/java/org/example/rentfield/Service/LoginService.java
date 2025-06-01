package org.example.rentfield.Service;

import org.example.rentfield.Model.DTO.UserLoginDTO;
import org.example.rentfield.Model.DTO.UserMapper;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.RegistrationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final RegistrationRepository registrationRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public LoginService(RegistrationRepository registrationRepository,
                        PasswordEncoder passwordEncoder,
                        UserMapper userMapper) {
        this.registrationRepository = registrationRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public Optional<User> findByEmail(String email) {
        return registrationRepository.findByEmail(email);
    }

    public UserLoginDTO validateCredentials(String email, String passwordRaw) {
        Optional<User>userResult = registrationRepository.findByEmail(email);
        System.out.println("passwordRaw PASSWORD: " + userResult.get().getPassword());
        System.out.println("user PASSWORD: " + userResult.get().getPassword());
        if (!userResult.map(user -> passwordEncoder.matches(passwordRaw, user.getPassword())).orElse(false)) {
            throw new RuntimeException("Incorrect password!");
        }
        System.out.println("RESULT PASSWORD: " + userResult.get().getPassword());
        UserLoginDTO userLoginDTO = userMapper.map(userResult.get());
        return userLoginDTO;
    }
}
