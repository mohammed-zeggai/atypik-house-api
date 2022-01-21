package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Notification;
import fr.atypikhouse.api.Repositories.NotificationRepository;
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

    @GetMapping
    public ResponseEntity<List<Notification>> getAll() {
        List<Notification> notifications = notificationRepository.findAll();
        return new ResponseEntity<List<Notification>>(notifications, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Notification> getOne(@PathVariable("id") Integer id) {
        Notification notification = notificationRepository.findById(id).get();
        return new ResponseEntity<Notification>(notification, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Notification> create(@RequestBody Notification notification) {
        notificationRepository.save(notification);
        return new ResponseEntity<Notification>(notification, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Notification> delete(@PathVariable("id") Integer id) {
        Notification notification = notificationRepository.findById(id).get();
        notificationRepository.delete(notification);

        return new ResponseEntity<Notification>(notification, HttpStatus.OK);
    }
}