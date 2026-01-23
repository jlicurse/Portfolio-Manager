package com.portfolio.portfolio_manager.service;

import com.portfolio.portfolio_manager.domain.Portfolio;
import com.portfolio.portfolio_manager.domain.Position; 
import org.springframework.stereotype.Service; 

import java.math.BigDecimal;
import java.util.*;

@Service
public class PortfolioService {

    private final List<Portfolio> portfolios;

    public PortfolioService() {
        List<Position> positions = List.of(
            new Position("AAPL", 25, new BigDecimal("145.00")),
            new Position("MSFT", 10, new BigDecimal("2725.00"))
        );
        this.portfolios = List.of(
            new Portfolio(UUID.randomUUID(), "Tech Growth", "Jonathan", positions)
        );
        
    }

    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public Optional<Portfolio> getPortfolioById(UUID id) {
        return portfolios.stream()
                .filter(portfolio -> portfolio.getId().equals(id))
                .findFirst();
    }
}


