package services;

import domain.User;
import repositories.UserRepository;

import java.util.Optional;

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordHasher hasher;

    public AuthenticationService(UserRepository userRepository, PasswordHasher hasher) {
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    public Optional<User> login(String usernameOrEmail, String password) {

        Optional<User> userOpt = userRepository.findByUsernameOrEmail(usernameOrEmail);

        if (userOpt.isEmpty()) return Optional.empty();

        User user = userOpt.get();

        String hash = hasher.hash(password, user.getCredential().getSalt());

        if (hash.equals(user.getCredential().getPasswordHash())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public PasswordHasher getHasher() {
        return hasher;
    }
}