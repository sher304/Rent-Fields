package org.example.rentfield.CustomException;

public class ReservationAlreadyUses extends RuntimeException {
    public ReservationAlreadyUses(String message) {
        super(message);
    }
}
