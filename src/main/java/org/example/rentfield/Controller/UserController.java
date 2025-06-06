package org.example.rentfield.Controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.rentfield.CustomException.NotAdminException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.DTO.BookingsAndReservationsDTO;
import org.example.rentfield.Service.User.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get my history of bookings, reservations and detailed profile information")
    @GetMapping("/api/v1/me")
    public ResponseEntity<?> getMyInformation() {
        try {
            return ResponseEntity.ok(userService.getMyInformation());
        }  catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all fields, which manager is responsible")
    @GetMapping("/api/v1/manage")
    public ResponseEntity<?> getManageInformation() {
        try {
            return ResponseEntity.ok(userService.getMyFields());
        } catch (NotAdminException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Get all bookings and reservations for a field which is manager linked.")
    @GetMapping("/api/v1/manage/bookingsAndReservations")
    public ResponseEntity<?> getBookingsAndReservations() {
        try {
            BookingsAndReservationsDTO bookingsAndReservationsDTO = userService.getAllBookingsAndReservations();
            return ResponseEntity.ok(bookingsAndReservationsDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
