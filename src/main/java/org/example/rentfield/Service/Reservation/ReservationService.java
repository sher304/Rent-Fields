package org.example.rentfield.Service.Reservation;

import org.example.rentfield.Model.DTO.ReservationDTO;
import org.example.rentfield.Model.Enums.BookingStatus;
import org.example.rentfield.Model.Enums.Role;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.Reservation;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.Field.FieldRepository;
import org.example.rentfield.Repository.Reservation.ReservationRepository;
import org.example.rentfield.Repository.User.RegistrationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final RegistrationRepository registrationRepository;
    private final FieldRepository fieldRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationMapper reservationMapper,
                              RegistrationRepository registrationRepository,
                              FieldRepository fieldRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.registrationRepository =  registrationRepository;
        this.fieldRepository = fieldRepository;
    }

    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase(Role.User.name())
                        || auth.getAuthority().equalsIgnoreCase(Role.User.name())
                        || auth.getAuthority().equalsIgnoreCase(Role.Manager.name()));
        if (!isAuthorized) {
            throw new RuntimeException("Not Authorized");
        }

        Optional<User> user = registrationRepository.findById(reservationDTO.getUserId());
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Optional<FootballField> field = fieldRepository.findById(reservationDTO.getFieldId());
        if (!field.isPresent()) {
            throw new RuntimeException("Field not found");
        }
        Reservation reservation = reservationMapper.map(reservationDTO, field.get(), user .get());
        reservation.setReservation_start(LocalDateTime.now());
        reservation.setStatus(BookingStatus.Pending);
        reservationRepository.save(reservation);
        return reservationMapper.map(reservation);
    }
}
