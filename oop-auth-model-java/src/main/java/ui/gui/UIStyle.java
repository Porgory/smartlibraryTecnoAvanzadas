package ui.gui;

import javax.swing.*;
import java.awt.*;

public class UIStyle {

    public static final Color BACKGROUND = new Color(245, 240, 230);
    public static final Color BUTTON = new Color(200, 180, 150);
    public static final Color TEXT = new Color(60, 50, 40);

    public static Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    public static Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static JButton createButton(String text) {
        JButton btn = new JButton(text);

        btn.setAlignmentX(Component.CENTER_ALIGNMENT); // 🔥 CENTRADO
        btn.setFocusPainted(false);
        btn.setBackground(BUTTON);
        btn.setForeground(TEXT);
        btn.setFont(NORMAL_FONT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
}