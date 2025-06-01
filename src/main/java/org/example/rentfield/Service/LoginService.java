package org.example.rentfield.Service;

import org.example.rentfield.Model.DTO.UserLoginDTO;
import org.example.rentfield.Model.DTO.UserMapper;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.RegistrationRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class LoginService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("THE EMAIL: " + email);
        User user = registrationRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
