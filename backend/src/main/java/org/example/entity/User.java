package org.example.entity;

import jakarta.annotation.Nullable;
import org.example.auth.Role;
import ru.tinkoff.kora.database.jdbc.EntityJdbc;

@EntityJdbc
public record User(
        Long id,
        String fullName,
        String phone,

        @Nullable String gender,
        @Nullable String category,
        @Nullable Double discount,
        @Nullable String specialization,
        @Nullable Qualification qualification,
        @Nullable String passwordHash,

        Role role
) {}