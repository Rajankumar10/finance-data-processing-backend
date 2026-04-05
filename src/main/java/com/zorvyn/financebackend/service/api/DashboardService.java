package com.zorvyn.financebackend.service.api;

import com.zorvyn.financebackend.dto.DashboardSummaryResponse;

public interface DashboardService {

    // Builds summary values needed for dashboard UI.
    DashboardSummaryResponse getSummary();
}
