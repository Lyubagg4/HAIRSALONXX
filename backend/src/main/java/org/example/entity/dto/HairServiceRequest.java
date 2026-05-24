package org.example.entity.dto;

import ru.tinkoff.kora.json.common.annotation.Json;

@Json
public record HairServiceRequest(
        String name,
        String type,
        String hallType,
        Double price
) {}