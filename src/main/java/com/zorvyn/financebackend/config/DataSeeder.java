package com.zorvyn.financebackend.config;

import com.zorvyn.financebackend.enums.RecordType;
import com.zorvyn.financebackend.enums.Role;
import com.zorvyn.financebackend.enums.UserStatus;
import com.zorvyn.financebackend.model.AppUser;
import com.zorvyn.financebackend.model.FinancialRecord;
import com.zorvyn.financebackend.repository.AppUserRepository;
import com.zorvyn.financebackend.repository.FinancialRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner loadDemoData(AppUserRepository appUserRepository, FinancialRecordRepository financialRecordRepository) {
        return args -> {
            if (appUserRepository.count() == 0) {
                appUserRepository.saveAll(List.of(
                        createUser("Rajan Kumar", "rajankumar10022002@gmail.com", Role.ADMIN),
                        createUser("Deepak Kumar", "deepak@gmail.com", Role.ANALYST),
                        createUser("Sinjon Saha", "sinjon@gmail.com", Role.VIEWER)
                ));
            }

            if (financialRecordRepository.count() == 0) {
                financialRecordRepository.saveAll(List.of(
                        createRecord("5200.00", RecordType.INCOME, "Salary", 20, "Monthly salary"),
                        createRecord("800.00", RecordType.INCOME, "Freelance", 12, "Extra project"),
                        createRecord("1200.00", RecordType.EXPENSE, "Rent", 16, "House rent"),
                        createRecord("300.00", RecordType.EXPENSE, "Groceries", 8, "Weekly groceries"),
                        createRecord("210.00", RecordType.EXPENSE, "Transport", 4, "Travel cost")
                ));
            }
        };
    }

    private AppUser createUser(String name, String email, Role role) {
        AppUser user = new AppUser();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE);
        return user;
    }

    private FinancialRecord createRecord(String amount, RecordType type, String category, int daysAgo, String notes) {
        FinancialRecord record = new FinancialRecord();
        record.setAmount(new BigDecimal(amount));
        record.setType(type);
        record.setCategory(category);
        record.setRecordDate(LocalDate.now().minusDays(daysAgo));
        record.setNotes(notes);
        record.setCreatedAt(OffsetDateTime.now());
        record.setUpdatedAt(OffsetDateTime.now());
        return record;
    }
}
