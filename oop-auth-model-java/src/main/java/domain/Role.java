package domain;

import java.util.Objects;

public class Role {

    private final String id;
    private final String name;

    public Role(String id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
    }

    // Constructor simple (para cuando solo tienes el nombre)
    public Role(String name) {
        this.id = null;
        this.name = Objects.requireNonNull(name);
    }

    public String getId() { return id; }
    public String getName() { return name; }
}