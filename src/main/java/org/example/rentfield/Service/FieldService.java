package org.example.rentfield.Service;

import org.example.rentfield.Model.DTO.FieldDTO;
import org.example.rentfield.Model.DTO.FieldMapper;
import org.example.rentfield.Model.Enums.Role;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.User;
import org.example.rentfield.Repository.FieldRepository;
import org.example.rentfield.Repository.RegistrationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        System.out.println(authentication.toString());
        System.out.println("AUTH NAME " + authentication.getName());

        String email = authentication.getName();
        Optional<User> user = registrationRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new RuntimeException("Not found admin id");
        }

        if (!user.get().getRole().equals(Role.Admin)) {
            throw new RuntimeException("Not admin");
        }
        FootballField footballField = fieldMapper.map(fieldDTO, user.get());
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
}
