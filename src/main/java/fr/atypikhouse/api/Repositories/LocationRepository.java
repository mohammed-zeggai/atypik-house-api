package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    List<Location> findTop6ByOrderByIdDesc();

    @Query(value = "SELECT * FROM location l WHERE DATE(planning_start_date) >= ?1 AND DATE(planning_end_date) <= ?2", nativeQuery = true)
    List<Location> findAllByStartAndEndDate(LocalDate planningStartDate, LocalDate planningEndDate);

    Location findByType(String type);
}
