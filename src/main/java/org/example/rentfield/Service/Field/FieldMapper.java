package org.example.rentfield.Service.Field;

import org.example.rentfield.Model.DTO.FieldDTO;
import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.User;
import org.springframework.stereotype.Service;

@Service
public class FieldMapper {

    public FieldDTO map(FootballField footballField) {
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setDescription(footballField.getDescription());
        fieldDTO.setLocation(footballField.getLocation());
        fieldDTO.setTitle(footballField.getTitle());
        fieldDTO.setFieldId(fieldDTO.getFieldId());
        fieldDTO.setManager_id(footballField.getManager().getUser_id());
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
