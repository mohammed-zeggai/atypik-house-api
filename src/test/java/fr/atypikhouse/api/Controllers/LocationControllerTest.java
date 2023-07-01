package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Repositories.LocationRepository;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = fr.atypikhouse.api.AtypikHouseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Test
    public void testAddLocation() {
        Location location = new Location();
        location.setId(5);
        location.setTitre("Unit Test Location");
        location.setType("Appartement");
        location.setEquipements("Cuisine équipée, Wi-Fi...");
        location.setSurface("20");
        location.setDescription("Test Description");
        location.setAdresse("Test Adresse");
        location.setPlanningStartDate(new Date());
        location.setPlanningEndDate(new Date());
        location.setImage("");
        location.setPrix(120.0);
        location.setUser(null);

        HttpEntity<Location> entity = new HttpEntity<>(location, RequestUtils.buildHeadersWithToken());

        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", entity, String.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testNewestLocations() {

        HttpEntity<Location> entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());

        ResponseEntity<List<Location>> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/newest", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Location>>() {});

        assertEquals(6, responseEntity.getBody().size());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateLocation() {

        Location oldLocation = locationRepository.findById(5);
        Location location = new Location();
        location.setId(6);
        location.setTitre("New Location New Price Test");
        location.setType("Appartement");
        location.setEquipements("Cuisine équipée, Wi-Fi...");
        location.setSurface("20");
        location.setDescription("Test Description");
        location.setAdresse("Test Adresse");
        location.setPlanningStartDate(new Date());
        location.setPlanningEndDate(new Date());
        location.setImage("");
        location.setPrix(220.0);
        location.setUser(null);

        HttpEntity<Location> entity = new HttpEntity<>(location, RequestUtils.buildHeadersWithToken());

        ResponseEntity<Location> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/update/" + oldLocation.getId(), HttpMethod.PUT, entity, new ParameterizedTypeReference<Location>() {});

        assertEquals(220.0, responseEntity.getBody().getPrix());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteLocation() {

        Location location = locationRepository.findById(6);

        HttpEntity<Location> entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());

        ResponseEntity<Location> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/delete/" + location.getId(), HttpMethod.DELETE, entity, new ParameterizedTypeReference<Location>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
