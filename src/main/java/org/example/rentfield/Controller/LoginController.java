package org.example.rentfield.Controller;

import org.example.rentfield.Model.DTO.UserLoginDTO;
import org.example.rentfield.Model.DTO.UserResponseDTO;
import org.example.rentfield.Security.JwtService;
import org.example.rentfield.Service.Login.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LoginService loginService;

    public LoginController(LoginService loginService,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("api/v1/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
        );
        try {
            System.out.println("FROM VIEW: " + userLoginDTO.getEmail());
            UserDetails userDetails = loginService.loadUserByUsername(userLoginDTO.getEmail());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new UserResponseDTO(token));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
