package org.example.rentfield.Controller;

import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/me")
    public ResponseEntity<?> getMyInformation() {
        try {
            return ResponseEntity.ok(userService.getMyInformation());
        }  catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
