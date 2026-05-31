package org.example.controller;


import org.example.auth.Role;
import org.example.auth.RoleChecker;
import org.example.entity.dto.AdminRequest;
import org.example.entity.dto.AdminResponse;
import org.example.service.AdminService;
import ru.tinkoff.kora.common.Component;
import ru.tinkoff.kora.http.common.HttpMethod;
import ru.tinkoff.kora.http.common.annotation.Header;
import ru.tinkoff.kora.http.common.annotation.HttpRoute;
import ru.tinkoff.kora.http.server.common.annotation.HttpController;
import ru.tinkoff.kora.json.common.annotation.Json;

@Component
@HttpController
public class AdminController {

    private final AdminService adminService;
    private final RoleChecker roleChecker;

    public AdminController(AdminService adminService, RoleChecker roleChecker) {
        this.adminService = adminService;
        this.roleChecker = roleChecker;
    }

    @Json
    @HttpRoute(method = HttpMethod.POST, path = "/api/admins")
    public AdminResponse createAdmin(
            @Header("Authorization") String auth,
            @Json AdminRequest request
    ) {
        roleChecker.requireRole(auth, Role.MANAGER);
        return adminService.createAdmin(request);
    }
}
