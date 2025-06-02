package org.example.rentfield.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.rentfield.Model.Booking;
import org.example.rentfield.Model.Reservation;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDTO {
    private int userId;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Booking> bookings;
    private List<Reservation> reservations;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
