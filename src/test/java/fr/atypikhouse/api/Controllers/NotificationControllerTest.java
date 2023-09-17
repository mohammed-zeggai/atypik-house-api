package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Notification;
import fr.atypikhouse.api.Repositories.NotificationRepository;
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
public class NotificationControllerTest {

    private static final String END_POINT_PATH = "api/notifications";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;


    @Test
    public void testAddNotification() {
        Notification notification = new Notification();
        notification.setId(5);
        notification.setMessage("Notification");
        notification.setDate(new Date());

        HttpEntity<Notification> entity = new HttpEntity<>(notification, RequestUtils.buildHeadersWithToken());

        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", entity, String.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteNotification() {

        Notification notification = notificationRepository.findById(5).get();

        HttpEntity<Notification> entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());

        ResponseEntity<Notification> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/delete/" + notification.getId(), HttpMethod.DELETE, entity, new ParameterizedTypeReference<Notification>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
