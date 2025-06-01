package org.example.rentfield.Repository.Booking;


import org.example.rentfield.Model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
