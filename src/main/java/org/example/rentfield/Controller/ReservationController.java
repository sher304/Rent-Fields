package org.example.rentfield.Controller;

import jakarta.validation.Valid;
import org.example.rentfield.CustomException.ReservationAlreadyUses;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.DTO.ReservationDTO;
import org.example.rentfield.Service.Reservation.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public  ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/api/v1/reservation")
    public ResponseEntity<?>  saveReservation(@Valid @RequestBody ReservationDTO reservationDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return  ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            ReservationDTO reservationResult  = reservationService.saveReservation(reservationDTO);
            return ResponseEntity.ok(reservationResult);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ReservationAlreadyUses e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
