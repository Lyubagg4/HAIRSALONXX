package org.example.entity.dto;

public record AdminRequest(
        String fullName,
        String phone,
        String password
) {
}