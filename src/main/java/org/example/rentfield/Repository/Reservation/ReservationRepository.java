package org.example.rentfield.Repository.Reservation;

import org.example.rentfield.Model.Reservation;
import org.example.rentfield.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

}
