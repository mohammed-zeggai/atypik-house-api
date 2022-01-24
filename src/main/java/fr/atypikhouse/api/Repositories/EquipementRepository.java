package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipementRepository extends JpaRepository<Equipement, Integer> {
}