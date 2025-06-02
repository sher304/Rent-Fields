package org.example.rentfield.Repository.Booking;


import org.example.rentfield.Model.Booking;
import org.example.rentfield.Model.FootballField;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    List<Booking> findAllByField_FieldIdIn(List<Integer> fieldIds);
}
