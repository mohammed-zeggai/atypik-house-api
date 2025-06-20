package fr.atypikhouse.api.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import fr.atypikhouse.api.Entities.Reservation;
import fr.atypikhouse.api.Utils.RequestUtils;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = fr.atypikhouse.api.AtypikHouseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerIntegrationTest {
    private static final String END_POINT_PATH = "/api/reservations";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddReservationIntegration() {
        // Given
        Reservation reservation = new Reservation();
        reservation.setPrix(120.00);
        reservation.setStartDate(new Date());
        reservation.setEndDate(new Date());
        reservation.setDate(new Date());

        // When
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", reservation, String.class);

        // Then
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateReservationIntegration() {
        // Given
        Reservation reservation = new Reservation();
        reservation.setId(6);
        reservation.setPrix(220.00);
        reservation.setStartDate(new Date());
        reservation.setEndDate(new Date());
        reservation.setDate(new Date());

        // When
        ResponseEntity<Reservation> responseEntity = restTemplate.exchange("http://localhost:" + port + END_POINT_PATH + "/update/6", HttpMethod.PUT, new HttpEntity<>(reservation), Reservation.class);

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteReservationIntegration() {
        // When
        ResponseEntity<Reservation> responseEntity = restTemplate.exchange("http://localhost:" + port + END_POINT_PATH + "/delete/6", HttpMethod.DELETE, new HttpEntity<>(RequestUtils.buildHeadersWithToken()), Reservation.class);

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
