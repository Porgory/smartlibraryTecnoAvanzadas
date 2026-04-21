package ui.gui;

import domain.*;
import repositories.*;
import services.*;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class AdminPanel extends JPanel {

    private final UserRepository userRepo;
    private final LibraryZoneRepository zoneRepo;
    private final AuthenticationService authService;
    private final ArduinoService arduino;

    public AdminPanel(UserRepository userRepo,
                      AuthenticationService authService,
                      ArduinoService arduino) {

        this.userRepo = userRepo;
        this.authService = authService;
        this.zoneRepo = new LibraryZoneRepository();
        this.arduino = arduino;

        setBackground(UIStyle.BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel title = new JLabel("Admin Panel");
        title.setFont(UIStyle.TITLE_FONT);
        title.setForeground(UIStyle.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createAdmin = UIStyle.createButton("Create Admin");
        JButton createZone = UIStyle.createButton("Create Zone");
        JButton viewZones = UIStyle.createButton("View Zones");
        JButton logout = UIStyle.createButton("Logout");

        add(title);
        add(Box.createVerticalStrut(25));
        add(createAdmin);
        add(Box.createVerticalStrut(10));
        add(createZone);
        add(Box.createVerticalStrut(10));
        add(viewZones);
        add(Box.createVerticalStrut(10));
        add(logout);

        // CREATE ADMIN
        createAdmin.addActionListener(e -> {

            String username = JOptionPane.showInputDialog("Username:");
            String email = JOptionPane.showInputDialog("Email:");
            String password = JOptionPane.showInputDialog("Password:");

            if (username == null || email == null || password == null ||
                username.isBlank() || email.isBlank() || password.isBlank()) {

                JOptionPane.showMessageDialog(this, "Invalid input ❌");
                return;
            }

            String salt = authService.getHasher().generateSalt();
            String hash = authService.getHasher().hash(password, salt);

            Credential cred = new Credential(hash, salt);

            User admin = new User(username, email, cred, "ADMIN");

            userRepo.save(admin);

            JOptionPane.showMessageDialog(this, "Admin created 👑");
        });

        // CREATE ZONE
        createZone.addActionListener(e -> {

            String id = UUID.randomUUID().toString();

            LibraryZone zone = new LibraryZone(id);
            zoneRepo.saveZone(zone);

            JOptionPane.showMessageDialog(this, "Zone created ✅");
        });

        // VIEW ZONES
        viewZones.addActionListener(e -> {
            new ZonesFrame(zoneRepo, arduino).setVisible(true);
        });

        // LOGOUT
        logout.addActionListener(e -> {

            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
            current.dispose();

            new LoginFrame(authService, userRepo, new LibraryController(), arduino).setVisible(true);
        });
    }
}