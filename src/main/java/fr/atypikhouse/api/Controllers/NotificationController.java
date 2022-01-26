package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Notification;
import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.NotificationRepository;
import fr.atypikhouse.api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<List<Notification>> getAllForUser(@PathVariable("id") Integer id) {
        User user = userRepository.findById(id).get();

        return new ResponseEntity<List<Notification>>(user.getNotifications(), HttpStatus.OK);
    }
}