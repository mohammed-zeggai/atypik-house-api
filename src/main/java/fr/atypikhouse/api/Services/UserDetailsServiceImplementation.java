package fr.atypikhouse.api.Services;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public UserDetailsServiceImplementation(UserRepository userRepository) {

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        fr.atypikhouse.api.Entities.User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        User userDetails = new User(user.getEmail(), user.getPassword(), Collections.EMPTY_LIST);
        return userDetails;
    }

    public fr.atypikhouse.api.Entities.User saveUser(fr.atypikhouse.api.Entities.User user) {
    }

    public List<fr.atypikhouse.api.Entities.User> getAllUsers() {
    }

    public void deleteUser(long userId) {
    }

    public Map<Object, Object> getUserById(Integer id) {
    }

    public fr.atypikhouse.api.Entities.User updatedUser(fr.atypikhouse.api.Entities.User user) {
    }
}