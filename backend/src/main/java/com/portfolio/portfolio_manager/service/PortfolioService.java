package com.portfolio.portfolio_manager.service;

import com.portfolio.portfolio_manager.domain.Portfolio;
import com.portfolio.portfolio_manager.domain.Position; 
import org.springframework.stereotype.Service; 

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PortfolioService {
    public List<Portfolio> getPortfolios() {
        List<Position> positions = List.of(
            new Position("AAPL", 25, new BigDecimal("145.00")),
            new Position("MSFT", 10, new BigDecimal("2725.00"))
        );

        return List.of(
            new Portfolio(UUID.randomUUID(), "Tech Growth", "Jonathan", positions)
        );
    }
}
