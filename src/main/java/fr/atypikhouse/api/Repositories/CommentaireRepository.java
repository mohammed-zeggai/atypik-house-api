package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
}
