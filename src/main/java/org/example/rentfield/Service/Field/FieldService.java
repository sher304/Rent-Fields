package org.example.rentfield.Service.Field;

import org.example.rentfield.CustomException.FieldNotFoundException;
import org.example.rentfield.CustomException.NotAdminException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.DTO.FieldDTO;
import org.example.rentfield.Model.Enums.Role;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.Field.FieldRepository;
import org.example.rentfield.Repository.User.RegistrationRepository;
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

    public FieldService(FieldMapper fieldMapper,
                        FieldRepository fieldRepository,
                        RegistrationRepository registrationRepository) {
        this.fieldMapper = fieldMapper;
        this.fieldRepository = fieldRepository;
        this.registrationRepository = registrationRepository;
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
        Optional<FootballField> field = fieldRepository.findById(id);
        if (!field.isPresent()) {
            throw new RuntimeException("Not found id");
        } else {
            return fieldMapper.map(field.get());
        }
    }

    public List<FieldDTO> getAllFields() {
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        fieldRepository.findAll().forEach(field -> fieldDTOList.add(fieldMapper.map(field)));
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
        return fields.stream()
                .map(fieldMapper::map)
                .collect(Collectors.toList());
    }

    public List<FieldDTO> getAvailableFields(LocalDateTime start, LocalDateTime end) {
        List<FootballField> allFields = (List<FootballField>) fieldRepository.findAll();
        List<FootballField> unavailableFields = fieldRepository.findUnavailableFields(start, end);
        List<FootballField> availableFields = allFields.stream()
                .filter(field -> !unavailableFields.contains(field))
                .toList();
        return availableFields.stream().map(fieldMapper::map).toList();
    }
}
