package fr.atypikhouse.api.Services;

import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDetailsServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .nom("Zeggai")
                .prenom("Mohammed")
                .email("mohammedzeggai9@gmail.com")
                .password("Password@2025")
                .build();
    }

    @Test
    public void should_return_exception_when_user_is_not_found() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        // then
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImplementation.loadUserByUsername("mohammedzeggai9@gmail.com");
        });
    }

}
