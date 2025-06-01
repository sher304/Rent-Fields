package org.example.rentfield.Repository;


import org.example.rentfield.Model.FootballField;
import org.example.rentfield.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FieldRepository extends CrudRepository<FootballField, Integer> {
}
