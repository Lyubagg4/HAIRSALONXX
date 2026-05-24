package org.example.entity.dto;

import org.example.entity.Qualification;
import ru.tinkoff.kora.json.common.annotation.Json;

@Json
public record MasterResponse(
        Long id,
        String fullName,
        String phone,
        String gender,
        String specialization,
        Qualification qualification
) {}