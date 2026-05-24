package org.example.entity;

import ru.tinkoff.kora.database.jdbc.EntityJdbc;

@EntityJdbc
public record VisitServiceEntity(
        Long id,
        Long visitId,
        Long serviceId,
        Double priceAtTime
) {}