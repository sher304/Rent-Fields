package org.example.rentfield.Controller;

import io.swagger.v3.oas.annotations.Operation;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService){
        this.fieldService = fieldService;
    }

    @Operation(summary = "Create a new Field", description = "Adds a new field, only access by admin.\nWhile creating, update status fo a user to manager, who is linked to the field.")
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

    @Operation(summary = "Show details of one field")
    @GetMapping("/api/v1/field/{id}")
    public ResponseEntity<?> getField(@PathVariable int id){
        try {
            FieldDTO fieldDTO = fieldService.getField(id);
            return ResponseEntity.ok().body(fieldDTO);
        } catch (FieldNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Get all fields")
    @GetMapping("/api/v1/fields")
    public ResponseEntity<List<FieldDTO>> getAllField(){
        return ResponseEntity.ok().body(fieldService.getAllFields());
    }

    @Operation(summary = "Delete field, only by admin access", description = "Delete all bookings etc, by cascade")
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


    @Operation(summary = "Search, by location, description and title.")
    @GetMapping("/api/v1/fields/search")
    public ResponseEntity<?> searchFields(@RequestParam String location) {
        List<FieldDTO> results = fieldService.searchFields(location);
        return ResponseEntity.ok(results);
    }


    @Operation(summary = "Filter fields, by time which is required")
    @GetMapping("/api/v1/fields/time")
    public ResponseEntity<List<FieldDTO>> getAllFieldsByTime(@RequestParam LocalDateTime start,
                                                             @RequestParam LocalDateTime end){
        List<FieldDTO> availableFields = fieldService.getAvailableFields(start, end);
        return ResponseEntity.ok(availableFields);
    }
}
