package services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class SimplePasswordHasher implements PasswordHasher {

    @Override
    public String hash(String rawPassword, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            String salted = rawPassword + salt;

            byte[] hashedBytes = md.digest(salted.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hashedBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(String rawPassword, String hash, String salt) {
        String newHash = hash(rawPassword, salt);
        return newHash.equals(hash);
    }

    @Override
    public String generateSalt() {
        return UUID.randomUUID().toString();
    }
}