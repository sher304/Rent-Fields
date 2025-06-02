package org.example.rentfield;

import org.example.rentfield.Service.Field.FieldService;
import org.example.rentfield.Service.Registatoin.RegistrationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class RentFieldApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RentFieldApplication.class, args);
        RegistrationService registrationService = context.getBean(RegistrationService.class);
        registrationService.saveAdminUser();

        FieldService fieldService = context.getBean(FieldService.class);
        fieldService.saveField();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
