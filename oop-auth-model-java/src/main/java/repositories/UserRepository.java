package repositories;

import domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsernameOrEmail(String value);
    void save(User user);
}