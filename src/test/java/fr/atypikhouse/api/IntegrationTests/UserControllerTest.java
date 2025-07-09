package fr.atypikhouse.api.IntegrationTests;

import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.UserRepository;
import fr.atypikhouse.api.Utils.RequestUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = fr.atypikhouse.api.AtypikHouseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    private static final String END_POINT_PATH = "/api/user";

    @LocalServerPort
    private String port;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    @Order(1)
    public void testAddUser() {
        User user = new User();
        user.setNom("TestNom");
        user.setPrenom("TestPrenom");
        user.setEmail("testemail@gmail.com");
        user.setPassword("Passwound@2025");
        user.setAdresse("TestAdresse");
        user.setDateNaissance(toDate(LocalDate.of(1999, 7, 15)));
        user.setTelephone("0707505480");
        user.setRole("ROLE_ADMIN");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        HttpEntity<User> entity = new HttpEntity<>(user, RequestUtils.buildHeadersWithToken());
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", entity, String.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void testUpdateUser() {

        User oldUser = userRepository.findByEmail("testemail@gmail.com");
        User user = new User();
        user.setNom("testNom");
        user.setPrenom("testPrenom");
        user.setEmail("testemail@gmail.com");
        user.setAdresse("testAdresse");
        user.setPassword("Passwound@2025");
        user.setDateNaissance(toDate(LocalDate.of(2000, 7, 15)));
        user.setTelephone("0750025555");
        user.setRole("ROLE_ADMIN");

        HttpEntity<User> entity = new HttpEntity<>(user, RequestUtils.buildHeadersWithToken());

        ResponseEntity<User> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/update/" + oldUser.getId(), HttpMethod.PUT, entity, new ParameterizedTypeReference<User>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void testDeleteUser() {

        User user = userRepository.findByEmail("testemail@gmail.com");

        HttpEntity<User> entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());

        ResponseEntity<User> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/delete/" + user.getId(), HttpMethod.DELETE, entity, new ParameterizedTypeReference<User>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
