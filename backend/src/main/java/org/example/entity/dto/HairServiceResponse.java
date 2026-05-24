package org.example.entity.dto;

import ru.tinkoff.kora.json.common.annotation.Json;

@Json
public record HairServiceResponse(
        Long id,
        String name,
        String type,
        String hallType,
        Double price
) {}