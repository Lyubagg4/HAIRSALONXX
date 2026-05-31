package org.example.controller;

import org.example.auth.Role;
import org.example.auth.RoleChecker;
import org.example.entity.dto.MasterRequest;
import org.example.entity.dto.MasterResponse;
import org.example.service.MasterService;
import ru.tinkoff.kora.common.Component;
import ru.tinkoff.kora.http.common.HttpMethod;
import ru.tinkoff.kora.http.common.annotation.Header;
import ru.tinkoff.kora.http.common.annotation.HttpRoute;
import ru.tinkoff.kora.http.common.annotation.Path;
import ru.tinkoff.kora.http.server.common.annotation.HttpController;
import ru.tinkoff.kora.json.common.annotation.Json;

import java.util.List;

@Component
@HttpController
public class MasterController {

    private final MasterService masterService;
    private final RoleChecker roleChecker;

    public MasterController(
            MasterService masterService,
            RoleChecker roleChecker
    ) {
        this.masterService = masterService;
        this.roleChecker = roleChecker;
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/masters")
    public List<MasterResponse> getAllMasters(
            @Header("Authorization") String auth
    ) {
        roleChecker.requireRole(auth, Role.ADMIN, Role.MANAGER);
        return masterService.getAllMasters();
    }

    @Json
    @HttpRoute(method = HttpMethod.POST, path = "/api/masters")
    public MasterResponse createMaster(
            @Header("Authorization") String auth,
            @Json MasterRequest request
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return masterService.createMaster(request);
    }

    @Json
    @HttpRoute(method = HttpMethod.PUT, path = "/api/masters/{id}")
    public MasterResponse updateMaster(
            @Header("Authorization") String auth,
            @Path Long id,
            @Json MasterRequest request
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return masterService.updateMaster(id, request);
    }

    @HttpRoute(method = HttpMethod.DELETE, path = "/api/masters/{id}")
    public void deleteMaster(
            @Header("Authorization") String auth,
            @Path Long id
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        masterService.deleteMaster(id);
    }
}