package com.zorvyn.financebackend.service.impl;

import com.zorvyn.financebackend.dto.CreateFinancialRecordRequest;
import com.zorvyn.financebackend.dto.FinancialRecordResponse;
import com.zorvyn.financebackend.dto.UpdateFinancialRecordRequest;
import com.zorvyn.financebackend.enums.RecordType;
import com.zorvyn.financebackend.exception.ApiException;
import com.zorvyn.financebackend.mapper.FinanceMapper;
import com.zorvyn.financebackend.model.FinancialRecord;
import com.zorvyn.financebackend.repository.FinancialRecordRepository;
import com.zorvyn.financebackend.service.api.FinancialRecordService;
import com.zorvyn.financebackend.validator.RecordFilterValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class FinancialRecordServiceImpl implements FinancialRecordService {
    private final FinancialRecordRepository financialRecordRepository;
    private final RecordFilterValidator recordFilterValidator;

    public FinancialRecordServiceImpl(FinancialRecordRepository financialRecordRepository, RecordFilterValidator recordFilterValidator) {
        this.financialRecordRepository = financialRecordRepository;
        this.recordFilterValidator = recordFilterValidator;
    }

    @Override
    public List<FinancialRecordResponse> getRecords(RecordType type, String category, LocalDate startDate, LocalDate endDate) {
        // Validation is kept separate to keep service code simple.
        recordFilterValidator.validateDateRange(startDate, endDate);

        Specification<FinancialRecord> specification = Specification.where(null);
        if (type != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("type"), type));
        }
        if (category != null && !category.isBlank()) {
            specification = specification.and((root, query, cb) -> cb.equal(cb.lower(root.get("category")), category.trim().toLowerCase()));
        }
        if (startDate != null) {
            specification = specification.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("recordDate"), startDate));
        }
        if (endDate != null) {
            specification = specification.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("recordDate"), endDate));
        }

        return financialRecordRepository.findAll(specification).stream()
                .map(FinanceMapper::toFinancialRecordResponse)
                .toList();
    }

    @Override
    public FinancialRecordResponse getRecordById(Long id) {
        return FinanceMapper.toFinancialRecordResponse(getRecord(id));
    }

    @Override
    public FinancialRecordResponse createRecord(CreateFinancialRecordRequest request) {
        // Save basic record information entered by admin.
        FinancialRecord record = new FinancialRecord();
        record.setAmount(request.amount());
        record.setType(request.type());
        record.setCategory(request.category().trim());
        record.setRecordDate(request.recordDate());
        record.setNotes(request.notes());
        record.setCreatedAt(OffsetDateTime.now());
        record.setUpdatedAt(OffsetDateTime.now());
        return FinanceMapper.toFinancialRecordResponse(financialRecordRepository.save(record));
    }

    @Override
    public FinancialRecordResponse updateRecord(Long id, UpdateFinancialRecordRequest request) {
        FinancialRecord record = getRecord(id);

        if (request.amount() != null) {
            record.setAmount(request.amount());
        }
        if (request.type() != null) {
            record.setType(request.type());
        }
        if (request.category() != null) {
            record.setCategory(request.category().trim());
        }
        if (request.recordDate() != null) {
            record.setRecordDate(request.recordDate());
        }
        if (request.notes() != null) {
            record.setNotes(request.notes());
        }

        record.setUpdatedAt(OffsetDateTime.now());
        return FinanceMapper.toFinancialRecordResponse(financialRecordRepository.save(record));
    }

    @Override
    public void deleteRecord(Long id) {
        financialRecordRepository.delete(getRecord(id));
    }

    @Override
    public List<FinancialRecord> getAllRawRecords() {
        return financialRecordRepository.findAll();
    }

    private FinancialRecord getRecord(Long id) {
        return financialRecordRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Financial record not found."));
    }
}
