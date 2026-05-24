package org.example.controller;

import org.example.auth.Role;
import org.example.auth.RoleChecker;
import org.example.entity.dto.HairServiceRequest;
import org.example.entity.dto.HairServiceResponse;
import org.example.service.HairServiceService;
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
public class HairServiceController {

    private final HairServiceService hairServiceService;
    private final RoleChecker roleChecker;

    public HairServiceController(
            HairServiceService hairServiceService,
            RoleChecker roleChecker
    ) {
        this.hairServiceService = hairServiceService;
        this.roleChecker = roleChecker;
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/services")
    public List<HairServiceResponse> getAllServices(
            @Header("Authorization") String auth
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return hairServiceService.getAllServices();
    }

    @Json
    @HttpRoute(method = HttpMethod.POST, path = "/api/services")
    public HairServiceResponse createService(
            @Header("Authorization") String auth,
            @Json HairServiceRequest request
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return hairServiceService.createService(request);
    }

    @Json
    @HttpRoute(method = HttpMethod.PUT, path = "/api/services/{id}")
    public HairServiceResponse updateService(
            @Header("Authorization") String auth,
            @Path Long id,
            @Json HairServiceRequest request
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return hairServiceService.updateService(id, request);
    }

    @HttpRoute(method = HttpMethod.DELETE, path = "/api/services/{id}")
    public void deleteService(
            @Header("Authorization") String auth,
            @Path Long id
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        hairServiceService.deleteService(id);
    }
}