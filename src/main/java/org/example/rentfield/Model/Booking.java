package org.example.rentfield.Model;


import jakarta.persistence.*;
import org.example.rentfield.Model.Enums.BookingStatus;

import java.util.Date;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_id;
    private Date start_date;
    private Date end_date;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private FootballField field;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;
}
