package org.example.rentfield.CustomException;

public class NotAdminException extends RuntimeException {

    public NotAdminException(String message) {
        super(message);
    }
}

