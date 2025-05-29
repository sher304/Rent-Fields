package org.example.rentfield.Repository;

import org.example.rentfield.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}