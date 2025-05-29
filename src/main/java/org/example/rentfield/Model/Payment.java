package org.example.rentfield.Model;

import jakarta.persistence.*;
import org.example.rentfield.Model.Enums.PaymentStatus;

import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payment_id;
    private int amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private Date date;

    @OneToOne
    @JoinColumn(name = "booking_id",  nullable = true)
    private Booking booking;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = true)
    private Reservation reservation;
}
