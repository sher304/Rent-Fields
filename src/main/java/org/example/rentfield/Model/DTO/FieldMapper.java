package org.example.rentfield.Model.DTO;

import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.User;
import org.springframework.stereotype.Service;

@Service
public class FieldMapper {

    public FieldDTO map(FootballField footballField) {
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setDescription(footballField.getDescription());
        fieldDTO.setLocation(footballField.getLocation());
        footballField.setTitle(footballField.getTitle());
        footballField.setManager(footballField.getManager());
        return fieldDTO;
    }

    public FootballField map(FieldDTO fieldDTO, User manager) {
        FootballField footballField = new FootballField();
        footballField.setTitle(fieldDTO.getTitle());
        footballField.setDescription(fieldDTO.getDescription());
        footballField.setLocation(fieldDTO.getLocation());
        footballField.setManager(manager);
        return footballField;
    }
}
