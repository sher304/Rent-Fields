package org.example.rentfield.Service.User;

import org.example.rentfield.CustomException.NotAdminException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.Booking;
import org.example.rentfield.Model.DTO.BookingsAndReservationsDTO;
import org.example.rentfield.Model.DTO.FieldDTO;
import org.example.rentfield.Model.DTO.UserLoginDTO;
import org.example.rentfield.Model.DTO.UserProfileDTO;
import org.example.rentfield.Model.Enums.Role;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.Reservation;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.Booking.BookingRepository;
import org.example.rentfield.Repository.Field.FieldRepository;
import org.example.rentfield.Repository.Reservation.ReservationRepository;
import org.example.rentfield.Repository.User.RegistrationRepository;
import org.example.rentfield.Service.Field.FieldMapper;
import org.example.rentfield.Service.Registatoin.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RegistrationRepository registrationRepository;
    private final UserMapper userMapper;
    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;
    private final BookingRepository bookingRepository;
    private final ReservationRepository reservationRepository;

    public UserService(RegistrationRepository registrationRepository, UserMapper userMapper, FieldRepository fieldRepository, FieldMapper fieldMapper, BookingRepository bookingRepository, ReservationRepository reservationRepository) {
        this.registrationRepository = registrationRepository;
        this.userMapper = userMapper;
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
        this.bookingRepository = bookingRepository;
        this.reservationRepository = reservationRepository;
    }

    public UserProfileDTO getMyInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = registrationRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserProfileDTO profileDTO = userMapper.mapProfile(user);
        return profileDTO;
    }


    public List<FieldDTO> getMyFields() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = registrationRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        if(user.getRole() != Role.Manager){
            throw new NotAdminException("Is not manager!");
        }
        List<FootballField> fields = fieldRepository.findByFieldByEmail(email);
        return fields.stream().map(field -> fieldMapper.map(field)).collect(Collectors.toList());
    }

    public BookingsAndReservationsDTO getAllBookingsAndReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = registrationRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<FootballField> managedFields = fieldRepository.findByFieldByEmail(user.getEmail());
        if (managedFields.isEmpty()) {
            return new BookingsAndReservationsDTO(List.of(), List.of());
        }
        List<Integer> fieldIds = managedFields.stream()
                .map(FootballField::getFieldId)
                .toList();
        List<Booking> bookings = bookingRepository.findAllByField_FieldIdIn(fieldIds);
        List<Reservation> reservations = reservationRepository.findAllByField_FieldIdIn(fieldIds);
        return new BookingsAndReservationsDTO(bookings, reservations);
    }
}
