package com.zorvyn.financebackend.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardSummaryResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal netBalance,
        List<CategoryTotal> categoryTotals,
        List<RecentActivity> recentActivity,
        List<MonthlyTrend> monthlyTrends
) {
    public record CategoryTotal(String category, BigDecimal total) {
    }

    public record RecentActivity(Long id, String category, String type, BigDecimal amount, String date) {
    }

    public record MonthlyTrend(String month, BigDecimal income, BigDecimal expense, BigDecimal net) {
    }
}
