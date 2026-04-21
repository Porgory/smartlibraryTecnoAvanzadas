package ui.gui;

import services.*;
import repositories.UserRepository;
import domain.User;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginFrame extends JFrame {

    private final ArduinoService arduino;

    public LoginFrame(AuthenticationService authService,
                      UserRepository userRepo,
                      LibraryController controller,
                      ArduinoService arduino) {

        this.arduino = arduino;

        setTitle("Smart Library");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(UIStyle.BACKGROUND);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel title = new JLabel("Smart Library");
        title.setFont(UIStyle.TITLE_FONT);
        title.setForeground(UIStyle.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        styleField(userField, "Username");

        JPasswordField passField = new JPasswordField();
        styleField(passField, "Password");

        JButton loginBtn = UIStyle.createButton("Login");
        JButton registerBtn = UIStyle.createButton("Create User");

        JLabel status = new JLabel("");
        status.setForeground(Color.RED);
        status.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(25));
        panel.add(userField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(passField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(registerBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(status);

        add(panel);

        loginBtn.addActionListener(e -> {

            Optional<User> logged = authService.login(
                    userField.getText(),
                    new String(passField.getPassword())
            );

            if (logged.isPresent()) {
                dispose();
                new MainFrame(logged.get(), controller, userRepo, authService, arduino).setVisible(true);
            } else {
                status.setText("Invalid credentials ❌");
            }
        });

        registerBtn.addActionListener(e -> {
            new RegisterFrame(userRepo, authService.getHasher()).setVisible(true);
        });
    }

    private void styleField(JTextField field, String title) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(UIStyle.NORMAL_FONT);
        field.setBorder(BorderFactory.createTitledBorder(title));
    }
}