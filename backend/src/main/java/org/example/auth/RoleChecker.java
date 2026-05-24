package org.example.auth;

import ru.tinkoff.kora.common.Component;

@Component
public class RoleChecker {

    private final JwtService jwtService;

    public RoleChecker(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void requireRole(String authHeader, Role... allowedRoles) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Unauthorized");
        }

        String token = authHeader.substring(7);
        Role currentRole = jwtService.getRole(token);

        for (Role allowedRole : allowedRoles) {
            if (currentRole == allowedRole) {
                return;
            }
        }

        throw new RuntimeException("Forbidden");
    }
}