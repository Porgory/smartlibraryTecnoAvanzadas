package ui.gui;

import domain.LibraryZone;
import repositories.LibraryZoneRepository;
import services.ArduinoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ZonesFrame extends JFrame {

    private final LibraryZoneRepository repo;
    private final ArduinoService arduino;

    // 🔥 ESTE ES EL CONSTRUCTOR QUE TE FALTA
    public ZonesFrame(LibraryZoneRepository repo, ArduinoService arduino) {

        this.repo = repo;
        this.arduino = arduino;

        setTitle("Library Zones");
        setSize(400, 400);
        setLocationRelativeTo(null);

        DefaultListModel<String> model = new DefaultListModel<>();

        List<LibraryZone> zones = repo.findAll();

        for (LibraryZone z : zones) {
            model.addElement(z.getId());
        }

        JList<String> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton openBtn = new JButton("Open Zone");

        setLayout(new BorderLayout());
        add(new JScrollPane(list), BorderLayout.CENTER);
        add(openBtn, BorderLayout.SOUTH);

        openBtn.addActionListener(e -> {

            String selected = list.getSelectedValue();

            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a zone ❌");
                return;
            }

            // 🔥 PASAMOS ARDUINO
            new ZoneDetailFrame(selected, repo, arduino).setVisible(true);
        });
    }
}