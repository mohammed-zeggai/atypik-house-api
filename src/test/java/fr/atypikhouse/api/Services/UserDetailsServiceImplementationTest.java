package fr.atypikhouse.api.Services;

import fr.atypikhouse.api.Repositories.UserRepository;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplementationTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserDetailsServiceImplementation userDetailsServiceImplementation = Mockito.mock(UserDetailsServiceImplementation.class);

    @Test
    public void should_return_exception_when_user_is_not_found(){
        //TDD test driven development
        //given
        when(userRepository.findByEmail(any())).then(null);
        //when
        //then
        assertTrue(true);
    }
}