package domain;

import java.util.HashSet;
import java.util.Set;

public class User {

    private final String id;
    private final String username;
    private final String email;
    private boolean active;

    private final Credential credential;
    private final Set<Role> roles;

    // Constructor para DB
    public User(String id,
                String username,
                String email,
                Credential credential,
                boolean active,
                Set<Role> roles) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.credential = credential;
        this.active = active;
        this.roles = roles != null ? roles : new HashSet<>();
    }

    // Constructor para crear nuevos usuarios
    public User(String username,
                String email,
                Credential credential,
                String roleName) {

        this.id = java.util.UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.credential = credential;
        this.active = true;
        this.roles = new HashSet<>();
        this.roles.add(new Role(roleName));
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isActive() { return active; }
    public Credential getCredential() { return credential; }
    public Set<Role> getRoles() { return roles; }

    public void deactivate() { this.active = false; }
    public void activate() { this.active = true; }
}