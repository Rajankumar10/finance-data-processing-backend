package com.zorvyn.financebackend.service.impl;

import com.zorvyn.financebackend.dto.DashboardSummaryResponse;
import com.zorvyn.financebackend.enums.RecordType;
import com.zorvyn.financebackend.model.FinancialRecord;
import com.zorvyn.financebackend.service.api.DashboardService;
import com.zorvyn.financebackend.service.api.FinancialRecordService;
import com.zorvyn.financebackend.util.CommonDateUtil;
import com.zorvyn.financebackend.util.ServerDateUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final FinancialRecordService financialRecordService;

    public DashboardServiceImpl(FinancialRecordService financialRecordService) {
        this.financialRecordService = financialRecordService;
    }

    @Override
    public DashboardSummaryResponse getSummary() {
        // Dashboard calculations are done from all saved records.
        List<FinancialRecord> records = financialRecordService.getAllRawRecords().stream()
                .filter(Objects::nonNull)
                .toList();

        BigDecimal totalIncome = getTotal(records, RecordType.INCOME);
        BigDecimal totalExpense = getTotal(records, RecordType.EXPENSE);
        BigDecimal netBalance = totalIncome.subtract(totalExpense);

        Map<String, BigDecimal> categoryMap = new LinkedHashMap<>();
        for (FinancialRecord record : records) {
            String category = record.getCategory() == null || record.getCategory().isBlank() ? "Uncategorized" : record.getCategory();
            BigDecimal amount = record.getAmount() == null ? BigDecimal.ZERO : record.getAmount();
            categoryMap.merge(category, amount, BigDecimal::add);
        }

        List<DashboardSummaryResponse.CategoryTotal> categoryTotals = categoryMap.entrySet().stream()
                .map(entry -> new DashboardSummaryResponse.CategoryTotal(entry.getKey(), entry.getValue()))
                .toList();

        List<DashboardSummaryResponse.RecentActivity> recentActivity = records.stream()
                .filter(record -> record.getRecordDate() != null)
                .sorted(Comparator.comparing(FinancialRecord::getRecordDate).reversed())
                .limit(5)
                .map(record -> new DashboardSummaryResponse.RecentActivity(
                        record.getId(),
                        record.getCategory(),
                        record.getType() == null ? "UNKNOWN" : record.getType().name(),
                        record.getAmount() == null ? BigDecimal.ZERO : record.getAmount(),
                        CommonDateUtil.toText(record.getRecordDate())))
                .toList();

        Map<String, BigDecimal[]> monthMap = new LinkedHashMap<>();
        for (FinancialRecord record : records) {
            if (record.getRecordDate() == null) {
                continue;
            }
            // Index 0 stores income and index 1 stores expense.
            String month = ServerDateUtil.toMonth(record.getRecordDate());
            monthMap.putIfAbsent(month, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            BigDecimal amount = record.getAmount() == null ? BigDecimal.ZERO : record.getAmount();
            if (record.getType() == RecordType.INCOME) {
                monthMap.get(month)[0] = monthMap.get(month)[0].add(amount);
            } else if (record.getType() == RecordType.EXPENSE) {
                monthMap.get(month)[1] = monthMap.get(month)[1].add(amount);
            }
        }

        List<DashboardSummaryResponse.MonthlyTrend> monthlyTrends = monthMap.entrySet().stream()
                .map(entry -> new DashboardSummaryResponse.MonthlyTrend(
                        entry.getKey(),
                        entry.getValue()[0],
                        entry.getValue()[1],
                        entry.getValue()[0].subtract(entry.getValue()[1])))
                .toList();

        return new DashboardSummaryResponse(totalIncome, totalExpense, netBalance, categoryTotals, recentActivity, monthlyTrends);
    }

    private BigDecimal getTotal(List<FinancialRecord> records, RecordType type) {
        return records.stream()
                .filter(record -> record.getType() == type)
                .map(record -> record.getAmount() == null ? BigDecimal.ZERO : record.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
