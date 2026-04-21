package services;

import domain.LibraryZone;

import java.util.ArrayList;
import java.util.List;

public class LibraryController {

    private List<LibraryZone> zones = new ArrayList<>();

    public void addZone(LibraryZone zone) {
        zones.add(zone);
    }

    private LibraryZone findZone(String id) {
        return zones.stream()
                .filter(z -> z.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void handlePresence(String zoneId, boolean presence) {
        LibraryZone zone = findZone(zoneId);
        if (zone != null) {
            zone.updatePresence(presence);
        } else {
            System.out.println("Zone not found.");
        }
    }

    public void handleNoise(String zoneId, double level) {
        LibraryZone zone = findZone(zoneId);
        if (zone != null) {
            zone.updateNoise(level);
        } else {
            System.out.println("Zone not found.");
        }
    }

    public void turnLightsOn(String zoneId) {
        LibraryZone zone = findZone(zoneId);
        if (zone != null) {
            zone.turnLightsOn();
        }
    }

    public void turnLightsOff(String zoneId) {
        LibraryZone zone = findZone(zoneId);
        if (zone != null) {
            zone.turnLightsOff();
        }
    }
}