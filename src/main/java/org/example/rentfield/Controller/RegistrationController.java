package org.example.rentfield.Controller;

import org.example.rentfield.Model.DTO.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class RegistrationController {

    @PostMapping("/api/v1/registration")
    public ResponseEntity<Object> userRegistration(@RequestBody UserRequestDTO userDTO) {
        System.out.println("User: " + userDTO.toString());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/hello")
    public ResponseEntity<Object> userRegistration() {
        System.out.println("HELLLO");
        return ResponseEntity.ok().build();
    }
}
