package org.example.entity.dto;

import ru.tinkoff.kora.json.common.annotation.Json;

@Json
public record HairServiceRequest(
        String name,
        String category,
        String type,
        String hallType,
        Double price
) {
}