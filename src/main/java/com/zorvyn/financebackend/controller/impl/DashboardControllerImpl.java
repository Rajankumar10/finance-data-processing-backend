package com.zorvyn.financebackend.controller.impl;

import com.zorvyn.financebackend.controller.api.DashboardControllerApi;
import com.zorvyn.financebackend.dto.DashboardSummaryResponse;
import com.zorvyn.financebackend.enums.Role;
import com.zorvyn.financebackend.security.AuthorizationService;
import com.zorvyn.financebackend.service.api.DashboardService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardControllerImpl implements DashboardControllerApi {
    private final DashboardService dashboardService;
    private final AuthorizationService authorizationService;

    public DashboardControllerImpl(DashboardService dashboardService, AuthorizationService authorizationService) {
        this.dashboardService = dashboardService;
        this.authorizationService = authorizationService;
    }

    @Override
    public DashboardSummaryResponse getSummary() {
        // All active roles can open dashboard summary.
        authorizationService.requireRole(Role.VIEWER, Role.ANALYST, Role.ADMIN);
        return dashboardService.getSummary();
    }
}
