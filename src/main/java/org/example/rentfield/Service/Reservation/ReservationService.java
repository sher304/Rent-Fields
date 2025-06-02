package org.example.rentfield.Service.Reservation;

import org.aspectj.apache.bcel.classfile.Field;
import org.example.rentfield.CustomException.FieldNotFoundException;
import org.example.rentfield.CustomException.ReservationAlreadyUses;
import org.example.rentfield.CustomException.UserNotFoundException;
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

import javax.swing.text.html.Option;
import java.nio.file.ReadOnlyFileSystemException;
import java.time.LocalDateTime;
import java.util.List;
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
        String email = authentication.getName();
        User user = registrationRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase(Role.User.name())
                        || auth.getAuthority().equalsIgnoreCase(Role.User.name())
                        || auth.getAuthority().equalsIgnoreCase(Role.Manager.name()));
        if (!isAuthorized) {
            throw new RuntimeException("Not Authorized");
        }

        Optional<FootballField> field = fieldRepository.findById(reservationDTO.getFieldId());
        if (!field.isPresent()) {
            throw new FieldNotFoundException("Field not found");
        }

        List<Reservation> reservationExists = reservationRepository.findAllByField_FieldId(reservationDTO.getFieldId());
        for (Reservation existing : reservationExists) {
            LocalDateTime existingStart = existing.getTime_start();
            LocalDateTime existingEnd = existing.getTime_end();

            boolean isOverlap = !(reservationDTO.getTimeEnd().isBefore(existingStart) ||
                    reservationDTO.getTimeStart().isAfter(existingEnd));

            if (isOverlap) {
                throw new ReservationAlreadyUses("Reservation overlaps with an existing reservation.");
            }
        }

        Reservation reservation = reservationMapper.map(reservationDTO, field.get(), user);
        reservation.setReservationStart(LocalDateTime.now());
        reservation.setStatus(BookingStatus.Pending);
        reservationRepository.save(reservation);
        return reservationMapper.map(reservation);
    }
}
