package fr.atypikhouse.api.Services;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import fr.atypikhouse.api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        fr.atypikhouse.api.Entities.User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        User userDetails = new User(user.getEmail(), user.getPassword(), Collections.EMPTY_LIST);
        return userDetails;
    }
}