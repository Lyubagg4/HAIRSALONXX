package org.example.controller;

import org.example.auth.Role;
import org.example.auth.RoleChecker;
import org.example.entity.dto.VisitRequest;
import org.example.entity.dto.VisitResponse;
import org.example.service.VisitService;
import ru.tinkoff.kora.common.Component;
import ru.tinkoff.kora.http.common.HttpMethod;
import ru.tinkoff.kora.http.common.annotation.Header;
import ru.tinkoff.kora.http.common.annotation.HttpRoute;
import ru.tinkoff.kora.http.server.common.annotation.HttpController;
import ru.tinkoff.kora.json.common.annotation.Json;

import java.util.List;

@Component
@HttpController
public class VisitController {

    private final VisitService visitService;
    private final RoleChecker roleChecker;

    public VisitController(VisitService visitService, RoleChecker roleChecker) {
        this.visitService = visitService;
        this.roleChecker = roleChecker;
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/visits")
    public List<VisitResponse> getAllVisits(
            @Header("Authorization") String auth
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return visitService.getAllVisits();
    }

    @Json
    @HttpRoute(method = HttpMethod.POST, path = "/api/visits")
    public VisitResponse createVisit(
            @Header("Authorization") String auth,
            @Json VisitRequest request
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return visitService.createVisit(request);
    }
}