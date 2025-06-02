package org.example.rentfield.Model.DTO;

import org.example.rentfield.Model.Booking;
import org.example.rentfield.Model.Reservation;

import java.util.List;

public class BookingsAndReservationsDTO {
    private List<Booking> bookings;
    private List<Reservation> reservations;

    public BookingsAndReservationsDTO(List<Booking> bookings, List<Reservation> reservations) {
        this.bookings = bookings;
        this.reservations = reservations;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
