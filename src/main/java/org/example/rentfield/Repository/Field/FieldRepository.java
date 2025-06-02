package org.example.rentfield.Repository.Field;


import jakarta.validation.constraints.NotEmpty;
import org.example.rentfield.Model.FootballField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FieldRepository extends CrudRepository<FootballField, Integer> {
    @Query("SELECT f FROM FootballField f WHERE LOWER(f.location) LIKE %:location% OR LOWER(f.title) LIKE %:location% OR LOWER(f.description) LIKE %:location%")
    List<FootballField> searchByLocationLike(@Param("location") String location);
    @Query(""" 
    SELECT DISTINCT r.field FROM Reservation r
    WHERE r.status <> 'Free'
      AND r.time_start < :end
      AND r.time_end > :start
    """)
    List<FootballField> findUnavailableFields(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);

    @Query("SELECT f FROM FootballField f WHERE LOWER(f.manager.email) = LOWER(:email)")
    List<FootballField> findByFieldByEmail(@Param("email") String email);

}
