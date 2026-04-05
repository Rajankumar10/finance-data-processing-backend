package com.zorvyn.financebackend.controller.impl;

import com.zorvyn.financebackend.controller.api.FinancialRecordControllerApi;
import com.zorvyn.financebackend.dto.CreateFinancialRecordRequest;
import com.zorvyn.financebackend.dto.FinancialRecordResponse;
import com.zorvyn.financebackend.dto.UpdateFinancialRecordRequest;
import com.zorvyn.financebackend.enums.RecordType;
import com.zorvyn.financebackend.enums.Role;
import com.zorvyn.financebackend.security.AuthorizationService;
import com.zorvyn.financebackend.service.api.FinancialRecordService;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FinancialRecordControllerImpl implements FinancialRecordControllerApi {
    private final FinancialRecordService financialRecordService;
    private final AuthorizationService authorizationService;

    public FinancialRecordControllerImpl(FinancialRecordService financialRecordService, AuthorizationService authorizationService) {
        this.financialRecordService = financialRecordService;
        this.authorizationService = authorizationService;
    }

    @Override
    public List<FinancialRecordResponse> getRecords(RecordType type, String category, LocalDate startDate, LocalDate endDate) {
        // Analyst and admin can view records.
        authorizationService.requireRole(Role.ANALYST, Role.ADMIN);
        return financialRecordService.getRecords(type, category, startDate, endDate);
    }

    @Override
    public FinancialRecordResponse getRecord(Long id) {
        authorizationService.requireRole(Role.ANALYST, Role.ADMIN);
        return financialRecordService.getRecordById(id);
    }

    @Override
    public FinancialRecordResponse createRecord(CreateFinancialRecordRequest request) {
        // Only admin can add records.
        authorizationService.requireRole(Role.ADMIN);
        return financialRecordService.createRecord(request);
    }

    @Override
    public FinancialRecordResponse updateRecord(Long id, UpdateFinancialRecordRequest request) {
        authorizationService.requireRole(Role.ADMIN);
        return financialRecordService.updateRecord(id, request);
    }

    @Override
    public void deleteRecord(Long id) {
        authorizationService.requireRole(Role.ADMIN);
        financialRecordService.deleteRecord(id);
    }
}
