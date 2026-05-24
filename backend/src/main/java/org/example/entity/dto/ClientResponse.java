package org.example.entity.dto;

import ru.tinkoff.kora.json.common.annotation.Json;

@Json
public record ClientResponse(
        Long id,
        String fullName,
        String phone,
        String gender,
        String category,
        Double discount
) {}