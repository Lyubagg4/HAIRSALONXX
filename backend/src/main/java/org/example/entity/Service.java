package org.example.entity;

import ru.tinkoff.kora.database.jdbc.EntityJdbc;

@EntityJdbc
public record Service(
        Long id,
        String name,
        ServiceCategory category,
        String type,
        String hallType,
        Double price
) {
}