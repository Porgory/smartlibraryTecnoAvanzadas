package services;

import domain.User;

public class AuthorizationService {

    public boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("ADMIN"));
    }

    public boolean isUser(User user) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("USER"));
    }

    public boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(roleName));
    }
}