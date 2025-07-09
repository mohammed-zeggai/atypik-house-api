package fr.atypikhouse.api.UnitTests;

import fr.atypikhouse.api.Controllers.LocationController;
import fr.atypikhouse.api.Entities.Location;
import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.LocationRepository;
import fr.atypikhouse.api.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class LocationControllerTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LocationController locationController;

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Location location;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialiser un objet User pour les tests
        user = new User();
        user.setId(1);

        // Initialiser un objet Location pour les tests
        location = new Location();
        location.setTitre("Unit Test Location");
        location.setType("CabaneTest");
        location.setEquipements("Cuisine équipée, Wi-Fi...");
        location.setSurface("20");
        location.setDescription("Test Description");
        location.setAdresse("Test Adresse");
        location.setPlanningStartDate(toDate(LocalDate.of(2025, 7, 15)));
        location.setPlanningEndDate(toDate(LocalDate.of(2025, 12, 17)));
        location.setImage("");
        location.setPrix(120.0);
        location.setUser(null);
        location.setUser(user);
    }

    @Test
    @Order(1)
    void testGetAllLocations() {
        when(locationRepository.findAll()).thenReturn(Collections.singletonList(location));

        ResponseEntity<List<Location>> response = locationController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    void testGetNewestLocations() {
        when(locationRepository.findTop6ByOrderByIdDesc()).thenReturn(Arrays.asList(location));

        ResponseEntity<List<Location>> response = locationController.getNewest();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(locationRepository, times(1)).findTop6ByOrderByIdDesc();
    }

    @Test
    @Order(3)
    void testGetAllLocationsForUser() {
        // Initialiser un User avec une liste contenant la location de test
        User testUser = new User();
        testUser.setId(1);
        testUser.setLocations(Collections.singletonList(location));

        // Simuler la récupération du User par son id
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        // Appeler la méthode du contrôleur
        ResponseEntity<List<Location>> response = locationController.getAllForUser(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Unit Test Location", response.getBody().get(0).getTitre());

        // Vérifier que userRepository.findById a été appelé une fois
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    @Order(4)
    void testCreateLocation() {
        // Simuler la réponse du repository
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        // Créer l'entité Http avec le token et la location
        ResponseEntity<Location> response = locationController.create(location);

        // Vérifier la réponse
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unit Test Location", response.getBody().getTitre());
        assertEquals("CabaneTest", response.getBody().getType());
        assertEquals("Test Description", response.getBody().getDescription());

        // Vérifier que la méthode save a bien été appelée une fois
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    @Order(5)
    void testUpdateLocation() {
        // Création d'une location existante
        Location existingLocation = new Location();
        existingLocation.setId(1);
        existingLocation.setTitre("Old Title");
        existingLocation.setType("Old Type");
        existingLocation.setDescription("Old Description");

        // Simuler la récupération de la location existante
        when(locationRepository.findById(1)).thenReturn(Optional.of(existingLocation));

        // Créer la location mise à jour
        Location updatedLocation = new Location();
        updatedLocation.setId(1);
        updatedLocation.setTitre("Updated Title");
        updatedLocation.setType("Updated Type");
        updatedLocation.setDescription("Updated Description");

        // Simuler la sauvegarde de la location mise à jour
        when(locationRepository.save(any(Location.class))).thenReturn(updatedLocation);

        // Appeler la méthode du contrôleur pour mettre à jour la location
        ResponseEntity<Location> response = locationController.update(1, updatedLocation);

        // Vérifications
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitre());
        assertEquals("Updated Type", response.getBody().getType());
        assertEquals("Updated Description", response.getBody().getDescription());

        // Vérifier que la méthode save() a été appelée une fois
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    @Order(6)
    void testDeleteLocation() {
        // Créer une location à supprimer
        Location locationToDelete = new Location();
        locationToDelete.setId(1);
        locationToDelete.setTitre("Title to Delete");

        // Simuler la récupération de la location à supprimer
        when(locationRepository.findById(1)).thenReturn(Optional.of(locationToDelete));

        // Appeler la méthode du contrôleur pour supprimer la location
        ResponseEntity<Location> response = locationController.delete(1);

        // Vérifications de la réponse
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Title to Delete", response.getBody().getTitre());

        // Vérifier que la méthode delete() a été appelée une fois avec la bonne location
        verify(locationRepository, times(1)).delete(locationToDelete);
    }
}
