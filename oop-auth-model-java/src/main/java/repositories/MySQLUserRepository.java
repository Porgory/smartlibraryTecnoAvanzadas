package repositories;

import domain.*;
import infrastructure.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class MySQLUserRepository implements UserRepository {

    private Connection getConnection() throws Exception {
        return DatabaseConnection.getConnection();
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {

        String userQuery = """
            SELECT * FROM users
            WHERE username = ? OR email = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(userQuery)) {

            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            String userId = rs.getString("id");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String passwordHash = rs.getString("password_hash");
            String salt = rs.getString("salt");
            boolean active = rs.getBoolean("active");

            Credential credential = new Credential(passwordHash, salt);

            Set<Role> roles = getUserRoles(conn, userId);

            User user = new User(
                    userId,
                    username,
                    email,
                    credential,
                    active,
                    roles
            );

            return Optional.of(user);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Set<Role> getUserRoles(Connection conn, String userId) throws SQLException {

        String roleQuery = """
            SELECT r.id, r.name
            FROM roles r
            JOIN user_roles ur ON r.id = ur.role_id
            WHERE ur.user_id = ?
        """;

        Set<Role> roles = new HashSet<>();

        try (PreparedStatement stmt = conn.prepareStatement(roleQuery)) {

            stmt.setString(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                roles.add(new Role(
                        rs.getString("id"),
                        rs.getString("name")
                ));
            }
        }

        return roles;
    }

    @Override
    public void save(User user) {

        String insertUser = """
            INSERT INTO users (id, username, email, password_hash, salt, active)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        String findRole = "SELECT id FROM roles WHERE name = ?";
        String insertRole = "INSERT INTO roles (id, name) VALUES (?, ?)";
        String insertUserRole = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";

        try (Connection conn = getConnection()) {

            conn.setAutoCommit(false);

            // 🔹 Insert user
            try (PreparedStatement stmt = conn.prepareStatement(insertUser)) {

                stmt.setString(1, user.getId());
                stmt.setString(2, user.getUsername());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getCredential().getPasswordHash());
                stmt.setString(5, user.getCredential().getSalt());
                stmt.setBoolean(6, user.isActive());

                stmt.executeUpdate();
            }

            // 🔹 Roles
            for (Role role : user.getRoles()) {

                String roleId = null;

                // Buscar role
                try (PreparedStatement stmt = conn.prepareStatement(findRole)) {
                    stmt.setString(1, role.getName());
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        roleId = rs.getString("id");
                    }
                }

                // Crear role si no existe
                if (roleId == null) {

                    roleId = UUID.randomUUID().toString();

                    try (PreparedStatement stmt = conn.prepareStatement(insertRole)) {
                        stmt.setString(1, roleId);
                        stmt.setString(2, role.getName());
                        stmt.executeUpdate();
                    }
                }

                // Relación user_roles
                try (PreparedStatement stmt = conn.prepareStatement(insertUserRole)) {

                    stmt.setString(1, user.getId());
                    stmt.setString(2, roleId);

                    stmt.executeUpdate();
                }
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}