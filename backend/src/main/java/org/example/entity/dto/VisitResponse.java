package org.example.entity.dto;

import ru.tinkoff.kora.json.common.annotation.Json;

import java.time.LocalDate;
import java.util.List;

@Json
public record VisitResponse(
        Long id,
        Long clientId,
        Long masterId,
        LocalDate visitDate,
        Double totalCost,
        List<Long> serviceIds
) {}