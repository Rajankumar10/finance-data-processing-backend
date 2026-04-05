package com.zorvyn.financebackend.service.api;

import com.zorvyn.financebackend.dto.CreateFinancialRecordRequest;
import com.zorvyn.financebackend.dto.FinancialRecordResponse;
import com.zorvyn.financebackend.dto.UpdateFinancialRecordRequest;
import com.zorvyn.financebackend.enums.RecordType;
import com.zorvyn.financebackend.model.FinancialRecord;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordService {

    // Reads records with optional filters.
    List<FinancialRecordResponse> getRecords(RecordType type, String category, LocalDate startDate, LocalDate endDate);

    // Reads one record by id.
    FinancialRecordResponse getRecordById(Long id);

    // Creates a record.
    FinancialRecordResponse createRecord(CreateFinancialRecordRequest request);

    // Updates a record.
    FinancialRecordResponse updateRecord(Long id, UpdateFinancialRecordRequest request);

    // Deletes a record.
    void deleteRecord(Long id);

    // Used by dashboard service for aggregation logic.
    List<FinancialRecord> getAllRawRecords();
}
