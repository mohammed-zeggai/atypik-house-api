package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Entities.Notification;
import fr.atypikhouse.api.Entities.Reservation;
import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.LocationRepository;
import fr.atypikhouse.api.Repositories.NotificationRepository;
import fr.atypikhouse.api.Repositories.ReservationRepository;
import fr.atypikhouse.api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Reservation>> getAllForUser(@PathVariable("id") Integer id) {
        User user = userRepository.findById(id).get();

        return new ResponseEntity<List<Reservation>>(user.getReservations(), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Reservation> getOne(@PathVariable("id") Integer id) {
//        Reservation reservation = reservationRepository.findById(id).get();
//        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
//    }

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        reservation.setDate(new Date());
        reservationRepository.save(reservation);

        Location location = locationRepository.findById(reservation.getLocation().getId()).get();

        // Notifier
        Notification notification = new Notification();
        notification.setUser(location.getUser());
        notification.setMessage("Un client vient de réserver une de vos locations...");
        notification.setDate(new Date());
        notificationRepository.save(notification);

        return new ResponseEntity<Reservation>(reservation, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<Reservation> update(@PathVariable("id") Integer id, @RequestBody Reservation newReservation) {
        // Get the old reservation
        Reservation reservation = reservationRepository.findById(id).get();

        // Update the reservation with newReservation values
        reservation.setDate(newReservation.getDate());

        // Save the reservation
        reservationRepository.save(reservation);
        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Reservation> delete(@PathVariable("id") Integer id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservationRepository.delete(reservation);

        // Notifier
        Notification notification = new Notification();
        notification.setUser(reservation.getLocation().getUser());
        notification.setMessage("Un client vient d'annuler une réservation liée à une de vos locations...");
        notification.setDate(new Date());

        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }
}