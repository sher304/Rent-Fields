package org.example.rentfield.Controller;

import jakarta.validation.Valid;
import org.example.rentfield.Model.DTO.FieldDTO;
import org.example.rentfield.Repository.FieldRepository;
import org.example.rentfield.Service.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            System.out.println("Validation Error " +  result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        try {
            fieldService.addField(fieldDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/v1/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable int id){
        try {
            FieldDTO fieldDTO = fieldService.getField(id);
            return ResponseEntity.ok().body(fieldDTO);
        } catch (Exception e) {
            return  ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/v1/fields")
    public ResponseEntity<List<FieldDTO>> getAllField(){
        return ResponseEntity.ok().body(fieldService.getAllFields());
    }
}
