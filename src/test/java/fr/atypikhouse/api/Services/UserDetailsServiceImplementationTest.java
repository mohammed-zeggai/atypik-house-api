package fr.atypikhouse.api.Services;

import fr.atypikhouse.api.Entities.User;
import fr.atypikhouse.api.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        MockitoAnnotations.initMocks(this);
        user = User.builder()
                .id(1)
                .nom("Zeggai")
                .prenom("Mohammed")
                .email("mohammedzeggai9@gmail.com")
                .build();
    }

    @Test
    public void should_return_exception_when_user_is_not_found() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // then
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImplementation.loadUserByUsername("mohammedzeggai9@gmail.com");
        });
    }

    @DisplayName("JUnit test for getAllUsers method:")
    @Test
    public void givenUsersList_whenGetAllUsers_thenReturnUsersList() {
        // given
        User user1 = User.builder()
                .id(1)
                .prenom("Tony")
                .nom("Stark")
                .email("tony@gmail.com")
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user, user1));

        // when
        List<User> userList = userDetailsServiceImplementation.getAllUsers();

        // then
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getUserById method")
    @Test
    public void givenUserId_whenGetUserById_thenReturnUserObject() {
        // given
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // when
        User savedUser = userDetailsServiceImplementation.getUserById(user.getId()).get();

        // then
        assertThat(savedUser).isNotNull();
    }

    @DisplayName("JUnit test for updateUser method")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() {
        // given
        when(userRepository.save(any(User.class))).thenReturn(user);
        user.setEmail("ram@gmail.com");
        user.setPrenom("Ram");

        // when
        User updatedUser = userDetailsServiceImplementation.updatedUser(user);

        // then
        assertThat(updatedUser.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedUser.getPrenom()).isEqualTo("Ram");
    }

    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void givenUserId_whenDeleteUser_thenNothing() {
        // given
        long userId = 1;

        doNothing().when(userRepository).deleteById((int) userId);

        // when
        userDetailsServiceImplementation.deleteUser(userId);

        // then
        verify(userRepository, times(1)).deleteById((int) userId);
    }

}
