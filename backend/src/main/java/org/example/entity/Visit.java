package org.example.entity;

import ru.tinkoff.kora.database.jdbc.EntityJdbc;

import java.time.LocalDate;

@EntityJdbc
public record Visit(
        Long id,
        Long clientId,
        Long masterId,
        LocalDate visitDate,
        Double totalCost
) {}