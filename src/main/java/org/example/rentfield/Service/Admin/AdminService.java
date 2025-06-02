package org.example.rentfield.Service.Admin;

import jakarta.transaction.Transactional;
import org.example.rentfield.CustomException.NotAdminException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.Enums.Role;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.User.RegistrationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final RegistrationRepository registrationRepository;

    public AdminService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase(Role.Admin.name()));
        return isAdmin;
    }

    public List<User> getAllUsers() {
        if (isAdmin()) return (List<User>) registrationRepository.findAll();
        else throw new NotAdminException("Not admin!");
    }

    @Transactional
    public void deleteUser(int id) {
        System.out.println("CHECK THE ADMIN OR NOT!");
        if (isAdmin()) {
            registrationRepository.findById(id).orElseThrow(() -> new UserNotFoundException("no user with this id!"));
            registrationRepository.deleteById(id);
            System.out.println("DELETE THE ADMIN OR NOT!");
        }
        else throw new NotAdminException("Not admin!");
    }
}
