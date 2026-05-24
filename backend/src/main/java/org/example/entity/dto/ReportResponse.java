package org.example.entity.dto;

import ru.tinkoff.kora.json.common.annotation.Json;

@Json
public record ReportResponse(
        String title,
        String value
) {}