package ui.gui;

import repositories.UserRepository;
import services.*;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {

    public UserPanel(LibraryController controller,
                     UserRepository repo,
                     AuthenticationService authService,
                     ArduinoService arduino) {

        setLayout(new GridLayout(2, 1, 10, 10));

        JButton logout = new JButton("Logout");

        add(new JLabel("User Panel", SwingConstants.CENTER));
        add(logout);

        logout.addActionListener(e -> {

            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
            current.dispose();

            // 🔥 FIX AQUÍ
            new LoginFrame(authService, repo, controller, arduino).setVisible(true);
        });
    }
}