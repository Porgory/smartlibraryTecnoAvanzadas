package repositories;

import domain.User;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> byUsername = new HashMap<>();
    private final Map<String, User> byEmail = new HashMap<>();

    @Override
    public Optional<User> findByUsernameOrEmail(String value) {
        User u = byUsername.get(value);
        if (u == null) u = byEmail.get(value);
        return Optional.ofNullable(u);
    }

    @Override
    public void save(User user) {
        byUsername.put(user.getUsername(), user);
        byEmail.put(user.getEmail(), user);
    }
}
