package org.example.entity.dto;

import ru.tinkoff.kora.json.common.annotation.Json;

@Json
public record MasterRequest(
        String fullName,
        String phone,
        String gender,
        String specialization,
        String qualification
) {}