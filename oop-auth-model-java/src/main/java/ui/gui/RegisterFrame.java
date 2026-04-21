package ui.gui;

import domain.*;
import repositories.UserRepository;
import services.PasswordHasher;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private final UserRepository repo;
    private final PasswordHasher hasher;

    public RegisterFrame(UserRepository repo, PasswordHasher hasher) {
        this.repo = repo;
        this.hasher = hasher;

        setTitle("Create User");
        setSize(320, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));

        JTextField user = new JTextField();
        JTextField email = new JTextField();
        JPasswordField pass = new JPasswordField();
        JButton create = new JButton("Create");

        panel.add(new JLabel("Username"));
        panel.add(user);

        panel.add(new JLabel("Email"));
        panel.add(email);

        panel.add(new JLabel("Password"));
        panel.add(pass);

        panel.add(create);

        add(panel);

        create.addActionListener(e -> {

            String username = user.getText();
            String userEmail = email.getText();
            String password = new String(pass.getPassword());

            // 🔥 Validaciones
            if (username.isBlank() || userEmail.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(this, "All fields are required ❌");
                return;
            }

            // 🔥 VALIDACIÓN SIMPLE DEL EMAIL
            if (!userEmail.contains("@")) {
                JOptionPane.showMessageDialog(this, "Email must contain @ ❌");
                return;
            }

            // 🔐 Hash + salt
            String salt = hasher.generateSalt();
            String hash = hasher.hash(password, salt);

            Credential cred = new Credential(hash, salt);

            // Siempre USER
            User newUser = new User(
                    username,
                    userEmail,
                    cred,
                    "USER"
            );

            repo.save(newUser);

            JOptionPane.showMessageDialog(this, "User created ✅");
            dispose();
        });
    }
}