package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Repositories.LocationRepository;
import fr.atypikhouse.api.Repositories.UserRepository;
import fr.atypikhouse.api.Utils.RequestUtils;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = fr.atypikhouse.api.AtypikHouseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocationControllerTest {

    private static final String END_POINT_PATH = "/api/location";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    @Test
    @Order(1)
    public void testAddLocation() {
        Location location = new Location();
        location.setTitre("Unit Test Location");
        location.setType("CabaneTest");
        location.setEquipements("Cuisine équipée, Wi-Fi...");
        location.setSurface("20");
        location.setDescription("Test Description");
        location.setAdresse("Test Adresse");
        location.setPlanningStartDate(toDate(LocalDate.of(2025, 7, 15)));
        location.setPlanningEndDate(toDate(LocalDate.of(2025, 12, 17)));
        location.setImage("");
        location.setPrix(120.0);
        location.setUser(null);

        HttpEntity<Location> entity = new HttpEntity<>(location, RequestUtils.buildHeadersWithToken());
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", entity, String.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
    }


    @Test
    @Order(2)
    public void testUpdateLocation() {
        Optional<Location> oldLocation = locationRepository.findById(141);
        Location oldLocationEntity = oldLocation.orElseThrow(() -> new RuntimeException("Location introuvable"));

        Location location = new Location();
        location.setTitre("Update Test");
        location.setType("CabaneTest");
        location.setEquipements("Cuisine équipée, Wi-Fi...");
        location.setSurface("20");
        location.setDescription("Test Description");
        location.setAdresse("Test Adresse");
        location.setPlanningStartDate(toDate(LocalDate.of(2025, 8, 15)));
        location.setPlanningEndDate(toDate(LocalDate.of(2025, 12, 27)));
        location.setImage("");
        location.setPrix(220.0);
        location.setUser(null);

        HttpEntity<Location> entity = new HttpEntity<>(location, RequestUtils.buildHeadersWithToken());
        ResponseEntity<Location> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/update/" + oldLocationEntity.getId(), HttpMethod.PUT, entity, Location.class);

        assertEquals(220.0, responseEntity.getBody().getPrix());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }


    @Test
    @Order(3)
    public void testDeleteLocation() {
        Optional<Location> oldLocation = locationRepository.findById(141);
        Location oldLocationEntity = oldLocation.orElseThrow(() -> new RuntimeException("Location introuvable"));

        HttpEntity<Location> entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());
        ResponseEntity<Location> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/delete/" + oldLocationEntity.getId(), HttpMethod.DELETE, entity,Location.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
