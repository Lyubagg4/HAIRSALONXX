package org.example.entity.dto;

public record AdminResponse(
        Long id,
        String fullName,
        String phone,
        String role
) {
}
