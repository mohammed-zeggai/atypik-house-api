package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.Notification;
import fr.atypikhouse.api.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser(User user);
}