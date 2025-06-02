package org.example.rentfield.Service.Reservation;

import org.example.rentfield.Model.DTO.ReservationDTO;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.Reservation;
import org.example.rentfield.Model.User;
import org.springframework.stereotype.Service;

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
        reservationDTO.setFieldId(reservation.getField().getFieldId());
        reservationDTO.setTimeEnd(reservation.getTime_end());
        reservationDTO.setTimeStart(reservation.getTime_start());
        reservation.setUser(reservation.getUser());
        reservation.setReservationStart(reservation.getReservationStart());
        reservation.setStatus(reservation.getStatus());
        return reservationDTO;
    }
}
