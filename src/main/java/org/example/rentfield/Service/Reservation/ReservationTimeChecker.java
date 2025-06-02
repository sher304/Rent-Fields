package org.example.rentfield.Service.Reservation;

import org.example.rentfield.Model.Booking;
import org.example.rentfield.Model.Enums.BookingStatus;
import org.example.rentfield.Model.Reservation;
import org.example.rentfield.Repository.Reservation.ReservationRepository;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationTimeChecker {

    private final ReservationRepository reservationRepository;

    public ReservationTimeChecker(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkReservationTime() {
        LocalDateTime tenMinute = LocalDateTime.now().minusMinutes(10);
        List<Reservation> expired = reservationRepository.findByStatus(BookingStatus.Pending);
        for (Reservation res : expired) {
            if (res.getReservationStart().isAfter(tenMinute)) {
                reservationRepository.delete(res);
            }
        }
    }
}
