package org.example.rentfield.Service.Payment;

import org.example.rentfield.Model.*;
import org.example.rentfield.Model.DTO.PaymentDTO;
import org.example.rentfield.Model.Enums.BookingStatus;
import org.example.rentfield.Model.Enums.PaymentStatus;
import org.example.rentfield.Model.Enums.Role;
import org.example.rentfield.Repository.Booking.BookingRepository;
import org.example.rentfield.Repository.Field.FieldRepository;
import org.example.rentfield.Repository.Payment.PaymentDelegate;
import org.example.rentfield.Repository.Payment.PaymentRepository;
import org.example.rentfield.Repository.Reservation.ReservationRepository;
import org.example.rentfield.Repository.User.RegistrationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final FieldRepository fieldRepository;
    private final RegistrationRepository registrationRepository;
    private final PaymentMapper paymentMapper;
    private final ReservationRepository reservationRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          FieldRepository fieldRepository,
                          RegistrationRepository registrationRepository,
                          PaymentMapper paymentMapper,
                          ReservationRepository reservationRepository,
                          BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.fieldRepository = fieldRepository;
        this.registrationRepository = registrationRepository;
        this.paymentMapper = paymentMapper;
        this.reservationRepository = reservationRepository;
        this.bookingRepository = bookingRepository;
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = registrationRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase(Role.User.name())
                        || auth.getAuthority().equalsIgnoreCase(Role.User.name())
                        || auth.getAuthority().equalsIgnoreCase(Role.Manager.name()));

        if (!isAuthorized) throw new RuntimeException("User is not authorized!");

        Reservation reservation = reservationRepository.findById(paymentDTO.getReservation_id()).orElseThrow(() -> new RuntimeException("Reservation not found"));
        FootballField footballField = fieldRepository.findById(reservation.getField().getField_id()).orElseThrow(() -> new RuntimeException("Reservation not found"));
        Payment payment = paymentMapper.map(paymentDTO, reservation);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setPayment(payment);
        booking.setEnd_date(reservation.getTime_end());
        booking.setStart_date(reservation.getTime_start());
        booking.setStatus(BookingStatus.Busy);
        booking.setField(footballField);
        bookingRepository.save(booking);

        payment.setBooking(booking);
        payment.setStatus(PaymentStatus.Paid);
        paymentRepository.save(payment);

        reservation.setStatus(BookingStatus.Busy);
        reservationRepository.save(reservation);
        return paymentMapper.map(payment);
    }
}
