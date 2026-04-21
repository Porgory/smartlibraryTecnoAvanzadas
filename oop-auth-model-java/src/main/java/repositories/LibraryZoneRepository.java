package repositories;

import domain.*;
import infrastructure.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class LibraryZoneRepository {

    private Connection getConnection() throws Exception {
        return DatabaseConnection.getConnection();
    }

    public void saveZone(LibraryZone zone) {

        String insertZone = "INSERT INTO library_zones (id) VALUES (?)";
        String insertLight = "INSERT INTO lighting_sensors (id, zone_id, is_on, mode) VALUES (?, ?, ?, ?)";
        String insertNoise = "INSERT INTO noise_sensors (id, zone_id, noise_level, mode) VALUES (?, ?, ?, ?)"; // 🔥 FIX

        try (Connection conn = getConnection()) {

            conn.setAutoCommit(false);

            String zoneId = zone.getId();

            // zone
            try (PreparedStatement stmt = conn.prepareStatement(insertZone)) {
                stmt.setString(1, zoneId);
                stmt.executeUpdate();
            }

            // lighting
            try (PreparedStatement stmt = conn.prepareStatement(insertLight)) {
                stmt.setString(1, UUID.randomUUID().toString());
                stmt.setString(2, zoneId);
                stmt.setBoolean(3, false);
                stmt.setString(4, "AUTO");
                stmt.executeUpdate();
            }

            // noise 🔥 FIX
            try (PreparedStatement stmt = conn.prepareStatement(insertNoise)) {
                stmt.setString(1, UUID.randomUUID().toString());
                stmt.setString(2, zoneId);
                stmt.setDouble(3, 0);
                stmt.setString(4, "AUTO");
                stmt.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<LibraryZone> findAll() {

        List<LibraryZone> zones = new ArrayList<>();

        String query = "SELECT id FROM library_zones";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                zones.add(new LibraryZone(rs.getString("id")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return zones;
    }

    public void updateLight(String zoneId, boolean isOn) {

        String query = "UPDATE lighting_sensors SET is_on = ?, mode='MANUAL' WHERE zone_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, isOn);
            stmt.setString(2, zoneId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNoise(String zoneId, double level) {

        // 🔥 FIX AQUÍ TAMBIÉN
        String query = "UPDATE noise_sensors SET noise_level = ?, mode='MANUAL' WHERE zone_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, level);
            stmt.setString(2, zoneId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateLightFull(String zoneId, boolean isOn, String mode) {

    String query = "UPDATE lighting_sensors SET is_on = ?, mode = ? WHERE zone_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setBoolean(1, isOn);
        stmt.setString(2, mode);
        stmt.setString(3, zoneId);
        stmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void updateNoiseFull(String zoneId, int level, String mode) {

    String query = "UPDATE noise_sensors SET noise_level = ?, mode = ? WHERE zone_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, level);
        stmt.setString(2, mode);
        stmt.setString(3, zoneId);
        stmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public String getLightMode(String zoneId) {
    String sql = "SELECT mode FROM lighting_sensors WHERE zone_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, zoneId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getString("mode");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return "AUTO";
}

public boolean isLightOn(String zoneId) {
    String sql = "SELECT is_on FROM lighting_sensors WHERE zone_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, zoneId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getBoolean("is_on");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
}