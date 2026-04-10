package com.portfolio.portfolio_manager.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*

Integration-style endpoint tests using MockMvc
These tests run against the real Spring context and in-memory DB configuration

*/
@SpringBootTest
@AutoConfigureMockMvc
public class PortfolioControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    //----- Helpers -----

    private String createPortfolioAndGetId(String name, String owner) throws Exception {
        String body = """
                {"name": "%s", "owner": "%s"}
                """.formatted(name, owner);
        String response = mockMvc.perform(post("/api/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.owner").value(owner))
                .andReturn()
                .getResponse()
                .getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("id").asText();
    }

    private String addPositionAndGetPositionId(String portfolioId, String symbol, int quantity, double avgPrice) throws Exception {
        String body = """
                {"symbol": "%s", "quantity": %d, "avgPrice": %.2f}
                """.formatted(symbol, quantity, avgPrice);

        String json = mockMvc.perform(post("/api/portfolios/{id}/positions", portfolioId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(portfolioId))
            .andExpect(jsonPath("$.positions", notNullValue()))
            .andExpect(jsonPath("$.positions", not(empty())))
            .andExpect(jsonPath("$.positions[0].id", notNullValue()))
            .andReturn()
            .getResponse()
            .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(json);
        String positionId = jsonNode.get("positions").get(0).get("id").asText();

        org.junit.jupiter.api.Assertions.assertFalse(positionId.isBlank());

        return positionId;
    
    }

    //----- Tests -----

    @Test
    void createPortfolio_returns201_andPortfolioResponse() throws Exception {
        mockMvc.perform(post("/api/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "Test", "owner": "Jonathan"}
            """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.owner").value("Jonathan"));
    }

    @Test 
    void createPortfolio_withInvalidData_returns400_withFieldErrors() throws Exception {
        mockMvc.perform(post("/api/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "", "owner": ""}
            """))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect (jsonPath("$.fieldErrors.name", not(emptyString())))
                .andExpect (jsonPath("$.fieldErrors.owner", not(emptyString())))
                .andExpect(jsonPath("$.path").value("/api/portfolios"));

    }

    @Test
    void addPosition_toExistingPortfolio_returns201_andUpdatedPortfolio() throws Exception {
        String portfolioId = createPortfolioAndGetId("P1", "Jonathan"); 

         mockMvc.perform(post("/api/portfolios/{id}/positions", portfolioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"symbol":"AAPL","quantity":10,"avgPrice":120.00}
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(portfolioId))
                .andExpect(jsonPath("$.positions", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.positions[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$.positions[0].quantity").value(10))
                .andExpect(jsonPath("$.positions[0].avgPrice").value(120.00));
    }

    @Test
    void updatePosition_returns200_andUpdatesFields() throws Exception {
        String portfolioId = createPortfolioAndGetId("P2", "Jonathan");
        String positionId = addPositionAndGetPositionId(portfolioId, "AAPL", 10, 120.00);

        mockMvc.perform(put("/api/portfolios/{portfolioId}/positions/{positionId}", portfolioId, positionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"quantity":50,"avgPrice":125.50}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // verify the updated position exists in response
                .andExpect(jsonPath("$.id").value(portfolioId))
                .andExpect(jsonPath("$.positions[*].id", hasItem(positionId)))
                .andExpect(jsonPath("$.positions[?(@.id=='%s')].quantity".formatted(positionId)).value(hasItem(50)))
                .andExpect(jsonPath("$.positions[?(@.id=='%s')].avgPrice".formatted(positionId)).value(hasItem(125.50)));
    }

    @Test
    void updatePosition_invalidBody_returns400_withFieldErrors() throws Exception {
        String portfolioId = createPortfolioAndGetId("P3", "Jonathan");
        String positionId = addPositionAndGetPositionId(portfolioId, "AAPL", 10, 120.00);

        // missing quantity + avgPrice (or set invalid values depending on your annotations)
        mockMvc.perform(put("/api/portfolios/{portfolioId}/positions/{positionId}", portfolioId, positionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.fieldErrors.quantity", not(emptyString())))
                .andExpect(jsonPath("$.fieldErrors.avgPrice", not(emptyString())));
    }

    @Test
    void updatePosition_wrongPositionId_returns404() throws Exception {
        String portfolioId = createPortfolioAndGetId("P4", "Jonathan");

        // random UUID that won't exist
        String missingPositionId = "00000000-0000-0000-0000-000000000000";

        mockMvc.perform(put("/api/portfolios/{portfolioId}/positions/{positionId}", portfolioId, missingPositionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"quantity":50,"avgPrice":125.50}
                                """))
                .andExpect(status().isNotFound());
    }        
}

