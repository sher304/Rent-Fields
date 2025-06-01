package org.example.rentfield.Repository.User;

import org.example.rentfield.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegistrationRepository extends CrudRepository<User, Integer> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    @Override
    Optional<User> findById(Integer integer);
}