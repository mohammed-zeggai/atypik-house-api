package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Notification;
import fr.atypikhouse.api.Entities.Reservation;
import fr.atypikhouse.api.Repositories.ReservationRepository;
import fr.atypikhouse.api.Repositories.UserRepository;
import fr.atypikhouse.api.Utils.RequestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = fr.atypikhouse.api.AtypikHouseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    private static final String END_POINT_PATH = "/api/reservations";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testAddReservation() {

        Reservation reservation = new Reservation();
        reservation.setId(5);
        reservation.setPrix(120.00);
        reservation.setStartDate(new Date());
        reservation.setEndDate(new Date());
        reservation.setDate(new Date());

        // Notifier
        Notification notification = new Notification();
        notification.setMessage("Un client vient de réserver une de vos locations...");
        notification.setDate(new Date());

        HttpEntity<Reservation> entity = new HttpEntity<>(reservation, RequestUtils.buildHeadersWithToken());

        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", entity, String.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateReservation() {

        Reservation oldReservation = reservationRepository.findById(5).get();
        Reservation reservation = new Reservation();
        reservation.setId(6);
        reservation.setPrix(220.00);
        reservation.setStartDate(new Date());
        reservation.setEndDate(new Date());
        reservation.setDate(new Date());


        HttpEntity<Reservation> entity = new HttpEntity<>(reservation, RequestUtils.buildHeadersWithToken());

        ResponseEntity<Reservation> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/update/" + oldReservation.getId(), HttpMethod.PUT, entity, new ParameterizedTypeReference<Reservation>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteReservation() {

        Reservation reservation = reservationRepository.findById(6).get();

        HttpEntity<Reservation> entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());

        ResponseEntity<Reservation> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/delete/" + reservation.getId(), HttpMethod.DELETE, entity, new ParameterizedTypeReference<Reservation>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
