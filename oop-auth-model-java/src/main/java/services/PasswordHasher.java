package services;

public interface PasswordHasher {

    String hash(String rawPassword, String salt);

    boolean matches(String rawPassword, String hash, String salt);

    String generateSalt();
}