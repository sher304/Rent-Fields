package org.example.rentfield.Service.Registatoin;

import org.example.rentfield.Model.DTO.UserLoginDTO;
import org.example.rentfield.Model.DTO.UserProfileDTO;
import org.example.rentfield.Model.DTO.UserRequestDTO;
import org.example.rentfield.Model.Enums.Role;
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
        user.setRole(Role.User);
        return user;
    }

    public UserLoginDTO map(User user) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setUserID(user.getUser_id());
        userLoginDTO.setRole(user.getRole());
        return userLoginDTO;
    }

    public UserProfileDTO mapProfile(User user) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setName(user.getName());
        userProfileDTO.setReservations(user.getReservations());
        userProfileDTO.setPhoneNumber(user.getPhoneNumber());
        userProfileDTO.setBookings(user.getBookings());
        return userProfileDTO;
    }
}
