package org.example.rentfield.Controller;

import jakarta.validation.Valid;
import org.example.rentfield.CustomException.FieldNotFoundException;
import org.example.rentfield.CustomException.NotAdminException;
import org.example.rentfield.CustomException.UserNotFoundException;
import org.example.rentfield.Model.DTO.FieldDTO;
import org.example.rentfield.Service.Field.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService){
        this.fieldService = fieldService;
    }

    @PostMapping("/api/v1/field")
    public ResponseEntity<?> addField(@Valid @RequestBody FieldDTO fieldDTO,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        try {
            fieldService.addField(fieldDTO);
            return ResponseEntity.ok().build();
        } catch (NotAdminException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/api/v1/field/{id}")
    public ResponseEntity<?> getField(@PathVariable int id){
        try {
            FieldDTO fieldDTO = fieldService.getField(id);
            return ResponseEntity.ok().body(fieldDTO);
        } catch (FieldNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/api/v1/fields")
    public ResponseEntity<List<FieldDTO>> getAllField(){
        return ResponseEntity.ok().body(fieldService.getAllFields());
    }

    @DeleteMapping("/api/v1/field/{id}")
    public ResponseEntity<?> removeField(@PathVariable int id){
        try {
            fieldService.removeField(id);
            return ResponseEntity.ok().build();
        } catch (NotAdminException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (FieldNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
