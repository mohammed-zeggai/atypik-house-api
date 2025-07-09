package fr.atypikhouse.api.UnitTests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.UserRepository;
import fr.atypikhouse.api.Controllers.UserController;
import fr.atypikhouse.api.Exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialise les mocks
    }

    @Test
    @Order(1)
    public void testCreateUser_UserAlreadyExists() throws UserAlreadyExistsException {
        // Simuler l'existence de l'utilisateur
        User existingUser = new User();
        existingUser.setEmail("testemail@gmail.com");
        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(existingUser);

        // Créer un nouvel utilisateur
        User newUser = new User();
        newUser.setEmail("testemail@gmail.com");

        // Appeler la méthode du controller
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userController.create(newUser);
        });

        assertEquals("Cet adresse mail est déjà utilisée!", exception.getMessage());
    }

    @Test
    @Order(2)
    public void testCreateUser_Success() throws UserAlreadyExistsException {
        // Créer un utilisateur
        User user = new User();
        user.setEmail("testemail@gmail.com");
        user.setPassword("Password@2025");  // Mot de passe pour le test

        // Simuler l'absence d'utilisateur existant
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        // Mock l'encodage du mot de passe
        String encodedPassword = "$2a$10$7PzV0XBYN5y39G9r7Kv/4OZ8oG28ZC5RkOj63tZMCfAOy9Hlt8T6O";  // Valeur encodée
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);  // Mock de l'encodeur

        // Simuler l'enregistrement de l'utilisateur
        when(userRepository.save(user)).thenReturn(user);

        // Appeler la méthode du controller
        ResponseEntity<User> response = userController.create(user);

        // Vérifier la réponse
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        // Vérifie que le mot de passe retourné dans la réponse est vide
        assertEquals("", response.getBody().getPassword());

        // Vérifie que la méthode encode() a été appelée avec le bon mot de passe
        verify(bCryptPasswordEncoder).encode("Password@2025");
    }


    @Test
    @Order(3)
    public void testUpdateUser() {
        // Données d'entrée
        User existingUser = new User();
        existingUser.setId(8);
        existingUser.setEmail("testemail@gmail.com");
        existingUser.setRole("USER");

        User updatedUser = new User();
        updatedUser.setRole("ADMIN");

        // Simuler la récupération de l'utilisateur
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Appeler la méthode du controller
        ResponseEntity<User> response = userController.update(1, updatedUser);

        // Vérifier la réponse
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ADMIN", response.getBody().getRole());
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        // Simuler la récupération de l'utilisateur avec l'ID 8
        User userToDelete = new User();
        userToDelete.setId(8);

        when(userRepository.findById(8)).thenReturn(Optional.of(userToDelete)); // Simuler findById

        // Appeler la méthode delete du contrôleur
        ResponseEntity<User> response = userController.delete(8);

        // Vérifier que la réponse HTTP est correcte
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userToDelete, response.getBody());  // Comparer l'objet User à celui retourné

        // Vérifier que delete() a bien été appelé une seule fois sur userRepository avec l'utilisateur
        verify(userRepository, times(1)).delete(userToDelete);
    }

}
