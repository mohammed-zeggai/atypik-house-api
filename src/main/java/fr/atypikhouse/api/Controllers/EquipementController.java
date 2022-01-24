package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Equipement;
import fr.atypikhouse.api.Repositories.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipement")
public class EquipementController {

    @Autowired
    private EquipementRepository equipementRepository;

    @GetMapping
    public ResponseEntity<List<Equipement>> getAll() {
        List<Equipement> equipements = equipementRepository.findAll();
        return new ResponseEntity<List<Equipement>>(equipements, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getOne(@PathVariable("id") Integer id) {
        Equipement equipement = equipementRepository.findById(id).get();
        return new ResponseEntity<Equipement>(equipement, HttpStatus.OK);
    }

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<Equipement> create(@RequestBody Equipement equipement) {
        equipementRepository.save(equipement);
        return new ResponseEntity<Equipement>(equipement, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<Equipement> update(@PathVariable("id") Integer id, @RequestBody Equipement newEquipement) {
        // Get the old equipement
        Equipement equipement = equipementRepository.findById(id).get();

        // Update the equipement with newEquipement values
        equipement.setTitre(newEquipement.getTitre());
        equipement.setDescription(newEquipement.getDescription());

        // Save the equipement
        equipementRepository.save(equipement);
        return new ResponseEntity<Equipement>(equipement, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Equipement> delete(@PathVariable("id") Integer id) {
        Equipement equipement = equipementRepository.findById(id).get();
        equipementRepository.delete(equipement);

        return new ResponseEntity<Equipement>(equipement, HttpStatus.OK);
    }
}