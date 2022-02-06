package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Entities.Notification;
import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.LocationRepository;
import fr.atypikhouse.api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        List<Location> locations = locationRepository.findAll();
        return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
    }

    @GetMapping("/newest")
    public ResponseEntity<List<Location>> getNewest() {
        List<Location> locations = locationRepository.findTop6ByOrderByIdDesc();
        return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
    }

    @GetMapping("/forUser/{id}")
    public ResponseEntity<List<Location>> getAllForUser(@PathVariable("id") Integer id) {
        User user = userRepository.findById(id).get();

        return new ResponseEntity<List<Location>>(user.getLocations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getOne(@PathVariable("id") Integer id) {
        Location location = locationRepository.findById(id).get();
        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<Location> create(@RequestBody Location location) {
        locationRepository.save(location);
        return new ResponseEntity<Location>(location, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<Location> update(@PathVariable("id") Integer id, @RequestBody Location newLocation) {
        // Get the old location
        Location location = locationRepository.findById(id).get();

        // Update the location with newLocation values
        location.setTitre(newLocation.getTitre());
        location.setType(newLocation.getType());
        location.setEquipements(newLocation.getEquipements());
        location.setSurface(newLocation.getSurface());
        location.setDescription(newLocation.getDescription());
        location.setAdresse(newLocation.getAdresse());
        location.setPlanning(newLocation.getPlanning());
        location.setImage(newLocation.getImage());
        location.setPrix(newLocation.getPrix());

        // Save the location
        locationRepository.save(location);
        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Location> delete(@PathVariable("id") Integer id) {
        Location location = locationRepository.findById(id).get();
        locationRepository.delete(location);

        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }
}