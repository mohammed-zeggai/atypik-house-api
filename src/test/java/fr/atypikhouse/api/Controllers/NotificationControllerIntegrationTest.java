package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Repositories.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import fr.atypikhouse.api.Entities.Notification;
import fr.atypikhouse.api.Utils.RequestUtils;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = fr.atypikhouse.api.AtypikHouseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationControllerIntegrationTest {
    private static final String END_POINT_PATH = "api/notifications";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void testAddNotification() {
        // Given
        Notification notification = new Notification();
        notification.setMessage("Notification");
        notification.setDate(new Date());

        // When
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", notification, String.class);

        // Then
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteNotification() {
        // Given
        Notification notification = new Notification();
        notification.setId(5);
        notificationRepository.save(notification);

        // When
        ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port + END_POINT_PATH + "/delete/5", HttpMethod.DELETE, new HttpEntity<>(RequestUtils.buildHeadersWithToken()), Void.class);

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
