package fr.atypikhouse.api.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.UserRepository;
import fr.atypikhouse.api.Utils.RequestUtils;
import java.util.Date;

@SpringBootTest(classes = fr.atypikhouse.api.AtypikHouseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
    private static final String END_POINT_PATH = "/api/users";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddUserIntegration() {
        // Given
        User user = new User();
        user.setId(5);
        user.setNom("TestNom");
        user.setPrenom("TestPrenom");
        user.setEmail("testemail@gmail.com");
        user.setAdresse("TestAdresse");
        user.setDateNaissance(new Date());
        user.setTelephone("0707505480");
        user.setRole("Admin");

        // When
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", user, String.class);

        // Then
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateUserIntegration() {
        // Given
        User user = new User();
        user.setId(6);
        user.setNom("testNom");
        user.setPrenom("testPrenom");
        user.setEmail("test@gmail.com");
        user.setAdresse("testAdresse");
        user.setDateNaissance(new Date());
        user.setTelephone("0750025555");
        user.setRole("Admin");

        // When
        ResponseEntity<User> responseEntity = restTemplate.exchange("http://localhost:" + port + END_POINT_PATH + "/update/6", HttpMethod.PUT, new HttpEntity<>(user), new ParameterizedTypeReference<User>() {});

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteUserIntegration() {
        // Given
        User user = userRepository.findById(6).orElse(null);

        // When
        ResponseEntity<User> responseEntity = restTemplate.exchange("http://localhost:" + port + END_POINT_PATH + "/delete/6", HttpMethod.DELETE, new HttpEntity<>(RequestUtils.buildHeadersWithToken()), new ParameterizedTypeReference<User>() {});

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
