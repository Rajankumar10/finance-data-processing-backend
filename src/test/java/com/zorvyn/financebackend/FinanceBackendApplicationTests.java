package com.zorvyn.financebackend;

import com.zorvyn.financebackend.constants.HeaderConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class FinanceBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void viewerCanOpenDashboardSummary() throws Exception {
        mockMvc.perform(get("/api/dashboard/summary")
                        .header(HeaderConstants.USER_ID, "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIncome").exists());
    }

    @Test
    void viewerCannotOpenRecordList() throws Exception {
        mockMvc.perform(get("/api/records")
                        .header(HeaderConstants.USER_ID, "3"))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanCreateRecord() throws Exception {
        String body = """
                {
                  "amount": 1200.00,
                  "type": "INCOME",
                  "category": "Bonus",
                  "recordDate": "2026-04-01",
                  "notes": "Simple test record"
                }
                """;

        mockMvc.perform(post("/api/records")
                        .header(HeaderConstants.USER_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.category").value("Bonus"));
    }
}
