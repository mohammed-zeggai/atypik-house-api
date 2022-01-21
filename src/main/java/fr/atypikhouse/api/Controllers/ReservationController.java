package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Reservation;
import fr.atypikhouse.api.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Reservation> getOne(@PathVariable("id") Integer id) {
        Reservation reservation = reservationRepository.findById(id).get();
        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        reservationRepository.save(reservation);
        return new ResponseEntity<Reservation>(reservation, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Reservation> delete(@PathVariable("id") Integer id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservationRepository.delete(reservation);

        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }
}
