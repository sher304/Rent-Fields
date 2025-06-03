package org.example.rentfield.Service.Field;

import org.example.rentfield.CustomException.FieldNotFoundException;
import org.example.rentfield.CustomException.NotAdminException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.DTO.FieldDTO;
import org.example.rentfield.Model.DTO.ReviewDTO;
import org.example.rentfield.Model.Enums.Role;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.Review;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.Field.FieldRepository;
import org.example.rentfield.Repository.Raiting.RatingRepository;
import org.example.rentfield.Repository.User.RegistrationRepository;
import org.example.rentfield.Service.Rating.RatingMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FieldService {

    private final FieldMapper fieldMapper;
    private final FieldRepository fieldRepository;
    private final RegistrationRepository registrationRepository;
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    public FieldService(FieldMapper fieldMapper,
                        FieldRepository fieldRepository,
                        RegistrationRepository registrationRepository,
                        RatingRepository ratingRepository,
                        RatingMapper ratingMapper) {
        this.fieldMapper = fieldMapper;
        this.fieldRepository = fieldRepository;
        this.registrationRepository = registrationRepository;
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }


    public void addField(FieldDTO fieldDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase(Role.Admin.name()));
        if (!isAdmin) {
            throw new NotAdminException("Not admin");
        }

        Optional<User> manager = registrationRepository.findById(fieldDTO.getManager_id());
        if (!manager.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        manager.get().setRole(Role.Manager);
        registrationRepository.save(manager.get());
        FootballField footballField = fieldMapper.map(fieldDTO, manager.get());
        fieldRepository.save(footballField);
    }

    public FieldDTO getField(int id) {
        FootballField field = fieldRepository.findById(id).orElseThrow(() -> new FieldNotFoundException("Authorise to write review!"));
        List<ReviewDTO> reviews = ratingRepository.findAllByField_FieldId(field.getFieldId()).stream().map(ratingMapper::map).collect(Collectors.toList());
        return fieldMapper.map(field, reviews);

    }

    public List<FieldDTO> getAllFields() {
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        fieldRepository.findAll().forEach(field -> {
            List<ReviewDTO> reviews = ratingRepository
                    .findAllByField_FieldId(field.getFieldId())
                    .stream()
                    .map(ratingMapper::map)
                    .collect(Collectors.toList());
            fieldDTOList.add(fieldMapper.map(field, reviews));
        });
        return fieldDTOList;
    }

    public void removeField(int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase(Role.Admin.name()));
        if (!isAdmin) {
            throw new NotAdminException("Not admin!");
        }

        Optional<FootballField> field = fieldRepository.findById(id);
        if (!field.isPresent()) {
            throw new FieldNotFoundException("Field not found");
        }
        fieldRepository.deleteById(id);
    }

    public void saveField() {
        FootballField footballField = new FootballField();
        footballField.setManager(registrationRepository.findById(2).get());
        footballField.setTitle("A");
        footballField.setDescription("B");
        footballField.setLocation("C");
        footballField.setPrice_per_hour(140);
        fieldRepository.save(footballField);
    }

    public List<FieldDTO> searchFields(String location) {
        List<FootballField> fields = fieldRepository.searchByLocationLike(location.toLowerCase());
        return fields.stream().map(field -> {
            List<ReviewDTO> reviews = ratingRepository.findAllByField_FieldId(field.getFieldId())
                    .stream()
                    .map(ratingMapper::map)
                    .collect(Collectors.toList());
            return fieldMapper.map(field, reviews);
        }).collect(Collectors.toList());
    }

    public List<FieldDTO> getAvailableFields(LocalDateTime start, LocalDateTime end) {
        List<FootballField> allFields = (List<FootballField>) fieldRepository.findAll();
        List<FootballField> unavailableFields = fieldRepository.findUnavailableFields(start, end);

        List<FootballField> availableFields = allFields.stream()
                .filter(field -> !unavailableFields.contains(field))
                .toList();

        return availableFields.stream().map(field -> {
            List<ReviewDTO> reviews = ratingRepository.findAllByField_FieldId(field.getFieldId())
                    .stream()
                    .map(ratingMapper::map)
                    .collect(Collectors.toList());
            return fieldMapper.map(field, reviews);
        }).collect(Collectors.toList());
    }
}
