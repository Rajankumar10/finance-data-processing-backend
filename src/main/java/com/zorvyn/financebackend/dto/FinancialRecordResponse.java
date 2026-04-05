package com.zorvyn.financebackend.dto;

import com.zorvyn.financebackend.enums.RecordType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record FinancialRecordResponse(
        Long id,
        BigDecimal amount,
        RecordType type,
        String category,
        LocalDate recordDate,
        String notes,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
