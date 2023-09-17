package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.Notification;
import fr.atypikhouse.api.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}