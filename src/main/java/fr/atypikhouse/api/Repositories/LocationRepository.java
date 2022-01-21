package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
