package org.example.rentfield.Repository.Reservation;

import org.example.rentfield.Model.Enums.BookingStatus;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.Reservation;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    List<Reservation> findByStatus(BookingStatus status);
    List<Reservation> findAllByField_FieldId(int fieldId);
    List<Reservation> findAllByField_FieldIdIn(List<Integer> fieldIds);
}
