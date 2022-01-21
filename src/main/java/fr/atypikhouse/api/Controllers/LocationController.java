package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Repositories.LocationRepository;
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

    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        List<Location> locations = locationRepository.findAll();
        return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Location> getOne(@PathVariable("id") Integer id) {
        Location location = locationRepository.findById(id).get();
        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Location> create(@RequestBody Location location) {
        locationRepository.save(location);
        return new ResponseEntity<Location>(location, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Location> delete(@PathVariable("id") Integer id) {
        Location location = locationRepository.findById(id).get();
        locationRepository.delete(location);

        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }
}
