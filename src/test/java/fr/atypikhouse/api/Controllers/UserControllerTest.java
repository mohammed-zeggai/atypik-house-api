package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Commentaire;
import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Exceptions.UserAlreadyExistsException;
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
public class UserControllerTest {
    private static final String END_POINT_PATH = "/api/users";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId(5);
        user.setNom("TestNom");
        user.setPrenom("TestPrenom");
        user.setEmail("testemail@gmail.com");
        user.setAdresse("TestAdresse");
        user.setDateNaissance(new Date());
        user.setTelephone("0707505480");
        user.setRole("Admin");

        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new UserAlreadyExistsException("Cet adresse mail est déjà utilisée!");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // Remove password from returned entity
        user.setPassword("");
        HttpEntity<User> entity = new HttpEntity<>(user, RequestUtils.buildHeadersWithToken());

        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", entity, String.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateUser() {

        User oldUser = userRepository.findById(5);
        User user = new User();
        user.setId(6);
        user.setNom("testNom");
        user.setPrenom("testPrenom");
        user.setEmail("test@gmail.com");
        user.setAdresse("testAdresse");
        user.setDateNaissance(new Date());
        user.setTelephone("0750025555");
        user.setRole("Admin");

        HttpEntity<User> entity = new HttpEntity<>(user, RequestUtils.buildHeadersWithToken());

        ResponseEntity<User> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/update/" + oldUser.getId(), HttpMethod.PUT, entity, new ParameterizedTypeReference<User>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteUser() {

        User user = userRepository.findById(6);

        HttpEntity<User> entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());

        ResponseEntity<User> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/delete/" + user.getId(), HttpMethod.DELETE, entity, new ParameterizedTypeReference<User>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
