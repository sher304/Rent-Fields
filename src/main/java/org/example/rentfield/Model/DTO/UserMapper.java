package org.example.rentfield.Model.DTO;

import org.example.rentfield.Model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User map(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setPassword(userRequestDTO.getPassword());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        return user;
    }
}
