package ui.gui;

import domain.User;
import repositories.UserRepository;
import services.*;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(User user,
                     LibraryController controller,
                     UserRepository userRepo,
                     AuthenticationService authService,
                     ArduinoService arduino) {

        setTitle("Smart Library - " + user.getUsername());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        if (user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("ADMIN"))) {
            add(new AdminPanel(userRepo, authService, arduino));
        } else {
            // 🔥 FIX AQUÍ
            add(new UserPanel(controller, userRepo, authService, arduino));
        }
    }
}