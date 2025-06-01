package org.example.rentfield.Repository.Reservation;

import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class ReservationDelegate {
    private Set<Reservation> reservations;

    public ReservationDelegate(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
