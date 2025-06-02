package org.example.rentfield.Controller;

import org.example.rentfield.CustomException.NotAdminException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Repository.Reservation.ReservationRepository;
import org.example.rentfield.Service.Admin.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController()
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService =  adminService;
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/api/v1/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            adminService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (NotAdminException e) {
            System.out.println("NOT ADMIN: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (UserNotFoundException e) {
            System.out.println("USER NOT FOUND: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
