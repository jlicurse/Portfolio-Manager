package com.portfolio.portfolio_manager.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.portfolio.portfolio_manager.domain.Portfolio;
import com.portfolio.portfolio_manager.domain.Position;

import java.util.UUID;
import java.util.List;
import java.util.UUID;  

@RestController
public class PortfolioController {

    @GetMapping("/api/portfolios")
    public List<Portfolio> getPortfolios() {
        List<Position> positions = List.of(
            new Position("AAPL", 25, new java.math.BigDecimal("145.00")),
            new Position("MSFT", 10, new java.math.BigDecimal("2725.00"))
        );

        return List.of(
            new Portfolio(UUID.randomUUID(), "Tech Growth", "Jonathan", positions)
        );

    }
}
    

