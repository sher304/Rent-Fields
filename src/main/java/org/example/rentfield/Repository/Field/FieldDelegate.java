package org.example.rentfield.Repository.Field;

import org.example.rentfield.Model.FootballField;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class FieldDelegate {

    private Set<FootballField> footballFieldSet;

    public FieldDelegate(Set<FootballField> footballFieldSet) {
        this.footballFieldSet = footballFieldSet;
    }
}
