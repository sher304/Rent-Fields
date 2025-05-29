package org.example.rentfield;

import org.example.rentfield.Service.RegistrationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RentFieldApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RentFieldApplication.class, args);
        RegistrationService registrationService = context.getBean(RegistrationService.class);
    }

}
