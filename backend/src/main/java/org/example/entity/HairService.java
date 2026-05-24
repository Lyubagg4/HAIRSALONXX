package org.example.entity;

import ru.tinkoff.kora.database.jdbc.EntityJdbc;

@EntityJdbc
public record HairService(
        Long id,
        String name,
        String type,
        String hallType,
        Double price
) {}
