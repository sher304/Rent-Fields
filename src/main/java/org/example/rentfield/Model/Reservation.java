package org.example.rentfield.Model;

import jakarta.persistence.*;
import org.example.rentfield.Model.Enums.BookingStatus;

import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservation_id;
    private Date reservation_start;
    private Date time_start;
    private Date time_end;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private FootballField field;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Payment payment;
}
