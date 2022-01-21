package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
