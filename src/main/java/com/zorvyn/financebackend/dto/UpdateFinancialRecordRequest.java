package com.zorvyn.financebackend.dto;

import com.zorvyn.financebackend.enums.RecordType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateFinancialRecordRequest(
        @DecimalMin(value = "0.01", message = "amount must be greater than zero")
        BigDecimal amount,

        RecordType type,

        @Size(max = 100, message = "category can have at most 100 characters")
        String category,

        LocalDate recordDate,

        @Size(max = 500, message = "notes can have at most 500 characters")
        String notes
) {
}
