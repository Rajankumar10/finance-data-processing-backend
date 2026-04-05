package com.zorvyn.financebackend.controller.api;

import com.zorvyn.financebackend.dto.DashboardSummaryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/dashboard")
public interface DashboardControllerApi {

    // Gives summary data for dashboard cards and charts.
    @GetMapping("/summary")
    DashboardSummaryResponse getSummary();
}
