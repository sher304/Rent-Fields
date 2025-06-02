package org.example.rentfield.Service.User;

import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.DTO.UserLoginDTO;
import org.example.rentfield.Model.DTO.UserProfileDTO;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.User.RegistrationRepository;
import org.example.rentfield.Service.Registatoin.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RegistrationRepository registrationRepository;
    private final UserMapper userMapper;

    public UserService(RegistrationRepository registrationRepository, UserMapper userMapper) {
        this.registrationRepository = registrationRepository;
        this.userMapper = userMapper;
    }

    public UserProfileDTO getMyInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = registrationRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserProfileDTO profileDTO = userMapper.mapProfile(user);
        return profileDTO;
    }

}
