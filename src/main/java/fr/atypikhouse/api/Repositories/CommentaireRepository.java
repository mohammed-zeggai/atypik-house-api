package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.Commentaire;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;

public interface CommentaireRepository extends JpaAttributeConverter<Commentaire, Integer> {
}
