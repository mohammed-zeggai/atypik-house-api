package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getOne(@PathVariable("id") Integer id) {
        User user = userRepository.findById(id).get();
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") Integer id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
