package com.zorvyn.financebackend.controller.api;

import com.zorvyn.financebackend.dto.CreateFinancialRecordRequest;
import com.zorvyn.financebackend.dto.FinancialRecordResponse;
import com.zorvyn.financebackend.dto.UpdateFinancialRecordRequest;
import com.zorvyn.financebackend.enums.RecordType;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/records")
public interface FinancialRecordControllerApi {

    // Returns records and supports filters like type, category, and date range.
    @GetMapping
    List<FinancialRecordResponse> getRecords(
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);

    // Returns one record by id.
    @GetMapping("/{id}")
    FinancialRecordResponse getRecord(@PathVariable Long id);

    // Creates a new financial record.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    FinancialRecordResponse createRecord(@Valid @RequestBody CreateFinancialRecordRequest request);

    // Updates an existing financial record.
    @PatchMapping("/{id}")
    FinancialRecordResponse updateRecord(@PathVariable Long id, @Valid @RequestBody UpdateFinancialRecordRequest request);

    // Deletes a financial record by id.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRecord(@PathVariable Long id);
}
