package org.example.rentfield.Service.User;

import org.example.rentfield.CustomException.NotAdminException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.DTO.FieldDTO;
import org.example.rentfield.Model.DTO.UserLoginDTO;
import org.example.rentfield.Model.DTO.UserProfileDTO;
import org.example.rentfield.Model.Enums.Role;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.Field.FieldRepository;
import org.example.rentfield.Repository.User.RegistrationRepository;
import org.example.rentfield.Service.Field.FieldMapper;
import org.example.rentfield.Service.Registatoin.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RegistrationRepository registrationRepository;
    private final UserMapper userMapper;
    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;

    public UserService(RegistrationRepository registrationRepository, UserMapper userMapper, FieldRepository fieldRepository, FieldMapper fieldMapper) {
        this.registrationRepository = registrationRepository;
        this.userMapper = userMapper;
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    public UserProfileDTO getMyInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = registrationRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserProfileDTO profileDTO = userMapper.mapProfile(user);
        return profileDTO;
    }


    public List<FieldDTO> getMyFields() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = registrationRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        if(user.getRole() != Role.Manager){
            throw new NotAdminException("Is not manager!");
        }
        List<FootballField> fields = fieldRepository.findByFieldByEmail(email);
        return fields.stream().map(field -> fieldMapper.map(field)).collect(Collectors.toList());
    }
}
