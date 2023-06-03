//package fr.atypikhouse.api.Services;
//
//import fr.atypikhouse.api.Entities.User;
//import fr.atypikhouse.api.Repositories.UserRepository;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.runner.RunWith;
//import org.mockito.stubbing.Answer;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import static org.mockito.Mockito.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.springframework.security.core.userdetails.User.builder;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//
//
//
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class UserDetailsServiceImplementationTest {
//
//    @Mock
//    private UserRepository userRepository = Mockito.mock(UserRepository.class);
//
//    @InjectMocks
//    private UserDetailsServiceImplementation userDetailsServiceImplementation = Mockito.mock(UserDetailsServiceImplementation.class);
//
//    private  User user;
//
//    @BeforeEach
//    public void setup()
//    {
//        user = builder()
//                .id(1)
//                .nom("Zeggai")
//                .prenom("Mohammed")
//                .email("mohammedzeggai9@gmail.com")
//                .build();
//    }
//
//    // JUnit test for return exception when user is not found
//    @Test
//    public void should_return_exception_when_user_is_not_found(){
//        //TDD test driven development
//        //given
//        Mockito.when(userRepository.findByEmail(User.getEmail()).then((Answer<?> ) new User("mohammed", "mohammed", Collections.EMPTY_LIST)));
//        //when
//        //then
//        assertThrows(UsernameNotFoundException.class , () -> {
//            userDetailsServiceImplementation.loadUserByUsername("mohammedzeggai9@gmail.com");
//        } );
//    }
//
//    // JUnit test for getAllUsers method:
//    @DisplayName("JUnit test for getAllUsers method:")
//    @Test
//    public void givenUsersList_whenGetAllUsers_thenReturnUsersList(){
//        // given - precondition or setup
//
//        User user1 = User.builder()
//                .id(1)
//                .prenom("Tony")
//                .nom("Stark")
//                .email("tony@gmail.com")
//                .build();
//
//        given(userRepository.findAll()).willReturn(List.of(user,user1));
//
//        // when -  action or the behaviour that we are going test
//        List<User> userList = userDetailsServiceImplementation.getAllUsers();
//
//        // then - verify the output
//        assertThat(userList).isNotNull();
//        assertThat(userList.size()).isEqualTo(1);
//    }
//
//    // JUnit test for getUserById method
//    @DisplayName("JUnit test for getUserById method")
//    @Test
//    public void givenUserId_whenGetUserById_thenReturnUserObject(){
//        // given
//        given(userRepository.findById(1)).willReturn(Optional.of(user));
//
//        // when
//        User savedUser = userDetailsServiceImplementation.getUserById(user.getId()).get();
//
//        // then
//        assertThat(savedUser).isNotNull();
//
//    }
//
//    // JUnit test for updateUser method
//    @DisplayName("JUnit test for updateUser method")
//    @Test
//    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser(){
//        // given - precondition or setup
//        given(userRepository.save(user)).willReturn(user);
//        user.setEmail("ram@gmail.com");
//        user.setPrenom("Ram");
//        // when -  action or the behaviour that we are going test
//        User updatedUser = userDetailsServiceImplementation.updatedUser(user);
//
//        // then - verify the output
//        assertThat(User.getEmail()).isEqualTo("ram@gmail.com");
//        assertThat(updatedUser.getPrenom()).isEqualTo("Ram");
//    }
//
//    // JUnit test for deleteUser method
//    @DisplayName("JUnit test for deleteUser method")
//    @Test
//    public void givenUserId_whenDeleteUser_thenNothing(){
//        // given - precondition or setup
//        long userId = 1;
//
//        willDoNothing().given(userRepository).deleteById((int) userId);
//
//        // when -  action or the behaviour that we are going test
//        userDetailsServiceImplementation.deleteUser(userId);
//
//        // then - verify the output
//        verify(userRepository, times(1)).deleteById((int) userId);
//    }
//
//}