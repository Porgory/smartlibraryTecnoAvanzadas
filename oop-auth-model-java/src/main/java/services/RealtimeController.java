package services;

import repositories.LibraryZoneRepository;

public class RealtimeController {

    private final LibraryZoneRepository repo;
    private final ArduinoService arduino;
    private final String zoneId;

    private boolean running = true;

    public RealtimeController(LibraryZoneRepository repo,
                              ArduinoService arduino,
                              String zoneId) {

        this.repo = repo;
        this.arduino = arduino;
        this.zoneId = zoneId;

        start();
    }

    private void start() {

        new Thread(() -> {

            while (running) {
                try {

                    String mode = repo.getLightMode(zoneId);
                    boolean isOn = repo.isLightOn(zoneId);

                    if (mode.equals("AUTO")) {
                        arduino.sendCommand("AUTO");
                    } else {
                        if (isOn) {
                            arduino.sendCommand("MANUAL_ON");
                        } else {
                            arduino.sendCommand("MANUAL_OFF");
                        }
                    }

                    Thread.sleep(1500); // 🔥 cada 1.5 segundos

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void stop() {
        running = false;
    }
}