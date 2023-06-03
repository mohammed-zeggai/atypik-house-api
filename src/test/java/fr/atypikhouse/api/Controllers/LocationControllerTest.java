package fr.atypikhouse.api.Controllers;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Exceptions.LocationNotFoundException;
import fr.atypikhouse.api.Repositories.LocationRepository;
import fr.atypikhouse.api.Repositories.UserRepository;
import fr.atypikhouse.api.Utils.RequestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        HttpEntity entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());

        ResponseEntity<List<Location>> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/newest", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Location>>() {});

        assertEquals(6, responseEntity.getBody().size());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateLocation() {

        Location oldLocation = locationRepository.findByTitre("Unit Test Location");
        Location location = new Location();
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

        HttpEntity entity = new HttpEntity<>(location, RequestUtils.buildHeadersWithToken());

        ResponseEntity<Location> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/update/" + oldLocation.getId(), HttpMethod.PUT, entity, new ParameterizedTypeReference<Location>() {});

        assertEquals(220.0, responseEntity.getBody().getPrix());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteLocation() {

        Location location = locationRepository.findByTitre("New Location New Price Test");

        HttpEntity entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());

        ResponseEntity<Location> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/delete/" + location.getId(), HttpMethod.DELETE, entity, new ParameterizedTypeReference<Location>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
