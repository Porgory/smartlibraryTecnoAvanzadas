package ui;

import domain.*;
import repositories.*;
import services.*;
import ui.gui.LoginFrame;

import javax.swing.*;
import java.util.Optional;

public class ConsoleApp {

    public static void main(String[] args) {

        UserRepository userRepo = new MySQLUserRepository();
        PasswordHasher hasher = new SimplePasswordHasher();

        AuthenticationService authService =
                new AuthenticationService(userRepo, hasher);

        LibraryController controller = new LibraryController();

        // 🔥 ARDUINO
        ArduinoService arduino = new ArduinoService("COM3"); // cambia si es necesario

        seed(userRepo, hasher);

        SwingUtilities.invokeLater(() -> {
            new LoginFrame(authService, userRepo, controller, arduino).setVisible(true);
        });
    }

    private static void seed(UserRepository repo, PasswordHasher hasher) {

        Optional<User> existing = repo.findByUsernameOrEmail("admin");

        if (existing.isPresent()) return;

        String rawPassword = "admin123";

        String salt = hasher.generateSalt();
        String hash = hasher.hash(rawPassword, salt);

        Credential cred = new Credential(hash, salt);

        User admin = new User(
                "admin",
                "admin@mail.com",
                cred,
                "ADMIN"
        );

        repo.save(admin);
    }
}