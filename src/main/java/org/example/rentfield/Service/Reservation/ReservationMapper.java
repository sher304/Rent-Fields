package org.example.rentfield.Service.Reservation;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.example.rentfield.Model.DTO.ReservationDTO;
import org.example.rentfield.Model.Enums.BookingStatus;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.Reservation;
import org.example.rentfield.Model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationMapper {

    public Reservation map(ReservationDTO reservationDTO,
                           FootballField field,
                           User user) {
        Reservation reservation = new Reservation();
        reservation.setField(field);
        reservation.setTime_end(reservationDTO.getTimeEnd());
        reservation.setTime_start(reservationDTO.getTimeStart());
        reservation.setUser(user);
        return reservation;
    }

    public ReservationDTO map(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setFieldId(reservation.getField().getField_id());
        reservationDTO.setTimeEnd(reservation.getTime_end());
        reservationDTO.setTimeStart(reservation.getTime_start());
        reservation.setUser(reservation.getUser());
        reservation.setReservation_start(reservation.getReservation_start());
        reservation.setStatus(reservation.getStatus());
        return reservationDTO;
    }
}
