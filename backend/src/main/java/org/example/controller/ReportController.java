package org.example.controller;

import org.example.auth.Role;
import org.example.auth.RoleChecker;
import org.example.entity.dto.ReportResponse;
import org.example.service.ReportService;
import ru.tinkoff.kora.common.Component;
import ru.tinkoff.kora.http.common.HttpMethod;
import ru.tinkoff.kora.http.common.annotation.Header;
import ru.tinkoff.kora.http.common.annotation.HttpRoute;
import ru.tinkoff.kora.http.common.annotation.Query;
import ru.tinkoff.kora.http.server.common.annotation.HttpController;
import ru.tinkoff.kora.json.common.annotation.Json;

import java.time.LocalDate;
import java.util.List;

@Component
@HttpController
public class ReportController {

    private final ReportService reportService;
    private final RoleChecker roleChecker;

    public ReportController(ReportService reportService, RoleChecker roleChecker) {
        this.reportService = reportService;
        this.roleChecker = roleChecker;
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/reports/clients-by-date")
    public List<ReportResponse> getClientsByDate(
            @Header("Authorization") String auth,
            @Query LocalDate date
    ) {
        roleChecker.requireRole(auth, Role.ADMIN, Role.MANAGER);
        return reportService.getClientsByDate(date);
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/reports/master-income")
    public ReportResponse getMasterIncome(
            @Header("Authorization") String auth,
            @Query Long masterId
    ) {
        roleChecker.requireRole(auth, Role.ADMIN, Role.MANAGER);
        return reportService.getMasterIncome(masterId);
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/reports/popular-service")
    public ReportResponse getPopularService(
            @Header("Authorization") String auth
    ) {
        roleChecker.requireRole(auth, Role.ADMIN, Role.MANAGER);
        return reportService.getPopularService();
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/reports/gender-stats")
    public ReportResponse getGenderStats(
            @Header("Authorization") String auth
    ) {
        roleChecker.requireRole(auth, Role.ADMIN, Role.MANAGER);
        return reportService.getGenderStats();
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/reports/regular-clients")
    public ReportResponse getRegularClientsCount(
            @Header("Authorization") String auth
    ) {
        roleChecker.requireRole(auth, Role.ADMIN, Role.MANAGER);
        return reportService.getRegularClientsCount();
    }

    @Json
    @HttpRoute(method = HttpMethod.GET, path = "/api/reports/top-master")
    public ReportResponse getTopMaster(
            @Header("Authorization") String auth
    ) {
        roleChecker.requireRole(auth, Role.ADMIN, Role.MANAGER);
        return reportService.getTopMaster();
    }
}