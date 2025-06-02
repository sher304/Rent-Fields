package org.example.rentfield.CustomException;

public class ReservationNotFoundException extends RuntimeException {
 
    public ReservationNotFoundException(String message) {
        super(message);
    }
}

