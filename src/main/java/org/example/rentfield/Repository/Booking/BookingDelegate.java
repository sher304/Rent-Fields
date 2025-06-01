package org.example.rentfield.Repository.Booking;

import org.example.rentfield.Model.Booking;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class BookingDelegate {

    Set<Booking> bookings;
    public BookingDelegate(Set<Booking> bookings) {
        this.bookings =  bookings;
    }
}
