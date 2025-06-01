package org.example.rentfield;

import org.example.rentfield.Service.RegistrationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RentFieldApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RentFieldApplication.class, args);
        RegistrationService registrationService = context.getBean(RegistrationService.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
