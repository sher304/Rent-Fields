package org.example.rentfield.Repository.Field;


import org.example.rentfield.Model.FootballField;
import org.springframework.data.repository.CrudRepository;

public interface FieldRepository extends CrudRepository<FootballField, Integer> {
}
