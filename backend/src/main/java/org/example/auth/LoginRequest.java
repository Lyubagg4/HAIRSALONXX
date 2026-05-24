package org.example.auth;

import ru.tinkoff.kora.json.common.annotation.Json;

@Json
public record LoginRequest(
        String phone,
        String password
) {}