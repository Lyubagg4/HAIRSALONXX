package org.example.controller;

import org.example.auth.Role;
import org.example.auth.RoleChecker;
import org.example.entity.dto.ClientRequest;
import org.example.entity.dto.ClientResponse;
import org.example.service.ClientService;
import ru.tinkoff.kora.common.Component;
import ru.tinkoff.kora.http.common.HttpMethod;
import ru.tinkoff.kora.http.common.annotation.Header;
import ru.tinkoff.kora.http.common.annotation.HttpRoute;
import ru.tinkoff.kora.http.common.annotation.Path;
import ru.tinkoff.kora.http.common.header.HttpHeadersImpl;
import ru.tinkoff.kora.http.server.common.annotation.HttpController;
import ru.tinkoff.kora.json.common.annotation.Json;

import java.net.http.HttpHeaders;
import java.util.List;

@Component
@HttpController
public class ClientController {

    private final ClientService clientService;
    private final RoleChecker roleChecker;

    public ClientController(ClientService clientService, RoleChecker roleChecker) {
        this.clientService = clientService;
        this.roleChecker = roleChecker;
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/clients")
    public List<ClientResponse> getAllClients(
            @Header("Authorization") String auth
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return clientService.getAllClients();
    }

    @Json
    @HttpRoute(method = HttpMethod.POST, path = "/api/clients")
    public ClientResponse createClient(
            @Header("Authorization") String auth,
            @Json ClientRequest request
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return clientService.createClient(request);
    }

    @Json
    @HttpRoute(method = HttpMethod.PUT, path = "/api/clients/{id}")
    public ClientResponse updateClient(
            @Header("Authorization") String auth,
            @Path Long id,
            @Json ClientRequest request
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        return clientService.updateClient(id, request);
    }

    @HttpRoute(method = HttpMethod.DELETE, path = "/api/clients/{id}")
    public void deleteClient(
            @Header("Authorization") String auth,
            @Path Long id
    ) {
        roleChecker.requireRole(auth, Role.ADMIN);
        clientService.deleteClient(id);
    }
}