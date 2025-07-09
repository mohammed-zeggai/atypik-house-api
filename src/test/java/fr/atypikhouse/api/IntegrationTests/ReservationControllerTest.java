package fr.atypikhouse.api.IntegrationTests;

import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Entities.Reservation;
import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.LocationRepository;
import fr.atypikhouse.api.Repositories.ReservationRepository;
import fr.atypikhouse.api.Repositories.UserRepository;
import fr.atypikhouse.api.Utils.RequestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = fr.atypikhouse.api.AtypikHouseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationControllerTest {

    private static final String END_POINT_PATH = "/api/reservation";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private static Integer reservationId;

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    @Order(1)
    public void testAddReservation() {
        // Créer un utilisateur
        User user = new User();
        user.setNom("TestNom");
        user.setPrenom("TestPrenom");
        user.setEmail("testemail@gmail.com");
        user.setPassword("Passwound@2025");
        user.setAdresse("TestAdresse");
        user.setDateNaissance(new Date());
        user.setTelephone("0707505480");
        user.setRole("ROLE_ADMIN");
        // Sauvegarde via repository ou API
        user = userRepository.save(user);

        // Créer une location
        Location location = new Location();
        location.setTitre("Unit Test Location");
        location.setType("CabaneTest");
        location.setEquipements("Cuisine équipée, Wi-Fi...");
        location.setSurface("20");
        location.setDescription("Test Description");
        location.setAdresse("Test Adresse");
        location.setPlanningStartDate(toDate(LocalDate.of(2025, 8, 10)));
        location.setPlanningEndDate(toDate(LocalDate.of(2025, 12, 27)));
        location.setImage("");
        location.setPrix(120.0);
        location.setUser(null);
        // Sauvegarde via repository ou API
        location = locationRepository.save(location);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setLocation(location);
        reservation.setPrix(120.00);
        reservation.setStartDate(toDate(LocalDate.of(2025, 8, 15)));
        reservation.setEndDate(toDate(LocalDate.of(2025, 11, 15)));
        reservation.setDate(new Date());

        HttpEntity<Reservation> entity = new HttpEntity<>(reservation, RequestUtils.buildHeadersWithToken());
        ResponseEntity<Reservation> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", entity, Reservation.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    @Transactional
    public void testUpdateReservation() {
        // Récupérer la réservation par son ID (ici 63L)
        Reservation oldReservation = reservationRepository.findById(73)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        // (Optionnel) Forcer le chargement des collections lazy si besoin
        oldReservation.getLocation().getCommentaires().size();

        // Modifier les champs nécessaires
        oldReservation.setPrix(120.00);
        oldReservation.setStartDate(toDate(LocalDate.of(2025, 9, 10)));
        oldReservation.setEndDate(toDate(LocalDate.of(2025, 12, 27)));
        oldReservation.setDate(new Date());

        // Préparer la requête HTTP PUT en passant l'ID dans l'URL, pas l'objet complet
        HttpEntity<Reservation> entity = new HttpEntity<>(oldReservation, RequestUtils.buildHeadersWithToken());

        ResponseEntity<Reservation> responseEntity = this.restTemplate.exchange(
                "http://localhost:" + port + END_POINT_PATH + "/update/" + oldReservation.getId(),
                HttpMethod.PUT,
                entity,
                Reservation.class
        );

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void testDeleteReservation() {

        Optional<Reservation> oldReservation = reservationRepository.findById(73);
        Reservation oldReservationEntity = oldReservation.orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        HttpEntity<Reservation> entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());
        ResponseEntity<Reservation> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/delete/" + oldReservationEntity.getId(), HttpMethod.DELETE, entity,Reservation.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
