package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Commentaire;
import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Repositories.CommentaireRepository;
import fr.atypikhouse.api.Repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/commentaire")
public class CommentaireController {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/location/{id}")
    public ResponseEntity<List<Commentaire>> getAllByLocation(@PathVariable("id") Integer id) {
        Location location = locationRepository.findById(id).get();

        List<Commentaire> commentaires = location.getCommentaires();
        return new ResponseEntity<List<Commentaire>>(commentaires, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> getOne(@PathVariable("id") Integer id) {
        Commentaire commentaire = commentaireRepository.findById(id).get();
        return new ResponseEntity<Commentaire>(commentaire, HttpStatus.OK);
    }

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<Commentaire> create(@RequestBody Commentaire commentaire) {
        commentaire.setDate_ajout(new Date());
        commentaireRepository.save(commentaire);
        return new ResponseEntity<Commentaire>(commentaire, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<Commentaire> update(@PathVariable("id") Integer id, @RequestBody Commentaire newCommentaire) {
        // Get the old commentaire
        Commentaire commentaire = commentaireRepository.findById(id).get();

        // Update the commentaire with newCommentaire values
        commentaire.setCommentaire(newCommentaire.getCommentaire());
        commentaire.setDate_modification(new Date());

        // Save the commentaire
        commentaireRepository.save(commentaire);
        return new ResponseEntity<Commentaire>(commentaire, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Commentaire> delete(@PathVariable("id") Integer id) {
        Commentaire commentaire = commentaireRepository.findById(id).get();
        commentaireRepository.delete(commentaire);

        return new ResponseEntity<Commentaire>(commentaire, HttpStatus.OK);
    }
}