package org.example.rentfield.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class FootballField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int field_id;
    private String title;
    private String description;
    private String location;
    private int price_per_hour;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    private List<Review> reviews;
}
