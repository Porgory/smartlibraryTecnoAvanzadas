package ui.gui;

import repositories.LibraryZoneRepository;
import services.ArduinoService;
import services.RealtimeController;

import javax.swing.*;
import java.awt.*;

public class ZoneDetailFrame extends JFrame {

    private RealtimeController realtime;

    public ZoneDetailFrame(String zoneId,
                           LibraryZoneRepository repo,
                           ArduinoService arduino) {

        setTitle("Zone: " + zoneId);
        setSize(450, 420);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 🔥 LIGHTING SENSOR
        JLabel lightTitle = new JLabel("Lighting Sensor");

        JComboBox<String> lightMode =
                new JComboBox<>(new String[]{"AUTO", "MANUAL"});

        JToggleButton lightSwitch = new JToggleButton("OFF");

        lightSwitch.addActionListener(e -> {
            lightSwitch.setText(lightSwitch.isSelected() ? "ON" : "OFF");
        });

        // 🔥 NOISE SENSOR
        JLabel noiseTitle = new JLabel("Noise Sensor");

        JComboBox<String> noiseMode =
                new JComboBox<>(new String[]{"AUTO", "MANUAL"});

        JSlider noiseLevel = new JSlider(0, 100, 0);
        noiseLevel.setMajorTickSpacing(20);
        noiseLevel.setPaintTicks(true);
        noiseLevel.setPaintLabels(true);

        // 🔥 SAVE BUTTON
        JButton saveBtn = new JButton("SAVE");

        panel.add(lightTitle);
        panel.add(lightMode);
        panel.add(lightSwitch);
        panel.add(noiseTitle);
        panel.add(noiseMode);
        panel.add(noiseLevel);
        panel.add(saveBtn);

        add(panel);

        // 🔥 INICIAR CONTROL EN TIEMPO REAL
        realtime = new RealtimeController(repo, arduino, zoneId);

        // 🔥 GUARDAR CAMBIOS EN BD
        saveBtn.addActionListener(e -> {

            boolean isOn = lightSwitch.isSelected();
            String lightM = (String) lightMode.getSelectedItem();

            int noise = noiseLevel.getValue();
            String noiseM = (String) noiseMode.getSelectedItem();

            // 👉 Guardar en BD
            repo.updateLightFull(zoneId, isOn, lightM);
            repo.updateNoiseFull(zoneId, noise, noiseM);

            JOptionPane.showMessageDialog(this, "Saved ✅");
        });

        // 🔥 DETENER HILO AL CERRAR
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                realtime.stop();
            }
        });
    }
}