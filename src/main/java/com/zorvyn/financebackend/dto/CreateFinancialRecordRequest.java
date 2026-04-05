package com.zorvyn.financebackend.dto;

import com.zorvyn.financebackend.enums.RecordType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateFinancialRecordRequest(
        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.01", message = "amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "type is required")
        RecordType type,

        @NotBlank(message = "category is required")
        @Size(max = 100, message = "category can have at most 100 characters")
        String category,

        @NotNull(message = "recordDate is required")
        LocalDate recordDate,

        @Size(max = 500, message = "notes can have at most 500 characters")
        String notes
) {
}
