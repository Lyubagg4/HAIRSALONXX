package org.example.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ru.tinkoff.kora.common.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtService {

    private static final String SECRET = "dev-secret-change-me";

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String generateToken(Long userId, Role role) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("role", role.name())
                .withExpiresAt(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .sign(algorithm);
    }

    public Role getRole(String token) {
        var verifier = JWT.require(algorithm).build();
        var decoded = verifier.verify(token);
        return Role.valueOf(decoded.getClaim("role").asString());
    }

    public Long getUserId(String token) {
        var verifier = JWT.require(algorithm).build();
        var decoded = verifier.verify(token);
        return Long.valueOf(decoded.getSubject());
    }
}