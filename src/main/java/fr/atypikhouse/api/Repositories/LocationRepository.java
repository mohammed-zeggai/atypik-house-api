package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findTop6ByOrderByIdDesc();
}