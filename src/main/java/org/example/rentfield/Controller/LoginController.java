package org.example.rentfield.Controller;

import org.example.rentfield.Model.DTO.UserLoginDTO;
import org.example.rentfield.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("api/v1/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO) {
        if(loginService.validateCredentials(userLoginDTO.getEmail(), userLoginDTO.getPassword())) {
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
