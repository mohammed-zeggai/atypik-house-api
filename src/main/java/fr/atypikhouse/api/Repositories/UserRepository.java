package fr.atypikhouse.api.Repositories;

import fr.atypikhouse.api.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(Integer id);
    User findByEmail(String email);
}