package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.UserRepository;
import fr.atypikhouse.api.Exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController{
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userRepository.findAll();

        // Remove passwords from returned entities
        users.stream().forEach(user -> user.setPassword(""));

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable("id") Integer id) {
        User user = userRepository.findById(id).get();

        // Remove password from returned entity
        user.setPassword("");

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<User> getOneByEmail(@PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);

        // Remove password from returned entity
        user.setPassword("");

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/testCredentials/{email}/{password}")
    public ResponseEntity<String> testCredentials(@PathVariable("email") String email,
                                                  @PathVariable("password") String password) {

        User user = userRepository.findByEmail(email);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<String>("KO", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) throws UserAlreadyExistsException {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new UserAlreadyExistsException("Cet adresse mail est déjà utilisée!");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // Remove password from returned entity
        user.setPassword("");

        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping("/updateRole/{id}/{newRole}")
    public ResponseEntity<User> update(@PathVariable("id") Integer id, @PathVariable("newRole") String newRole) {
        // Get the user
        User user = userRepository.findById(id).get();

        // Change the role
        user.setRole(newRole);

        // Save the user
        userRepository.save(user);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Integer id, @RequestBody User newUser) {
        // Get the old user
        User user = userRepository.findById(id).get();

        // Update the user with newUser values
        user.setNom(newUser.getNom());
        user.setPrenom(newUser.getPrenom());
        user.setEmail(newUser.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        user.setAdresse(newUser.getAdresse());
        user.setDateNaissance(newUser.getDateNaissance());
        user.setImage(newUser.getImage());
        user.setTelephone(newUser.getTelephone());
        user.setRole(newUser.getRole());

        // Save the user
        userRepository.save(user);

        // Remove password from returned entity
        user.setPassword("");

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") Integer id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);

        // Remove password from returned entity
        user.setPassword("");

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}