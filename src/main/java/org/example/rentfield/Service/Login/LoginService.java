package org.example.rentfield.Service.Login;

import org.example.rentfield.Service.Registatoin.UserMapper;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.User.RegistrationRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


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
        User user = registrationRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
