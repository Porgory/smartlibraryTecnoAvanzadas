package domain;

import java.util.Objects;

public class Permission {
    private final String code;
    public Permission(String code) { this.code = Objects.requireNonNull(code); }
    public String getCode() { return code; }
}
