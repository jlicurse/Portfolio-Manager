package com.portfolio.portfolio_manager.service;

import com.portfolio.portfolio_manager.domain.Portfolio;
import com.portfolio.portfolio_manager.domain.Position; 
import org.springframework.stereotype.Service; 

import java.math.BigDecimal;
import java.util.*;

@Service
public class PortfolioService {

    private final List<Portfolio> portfolios = new ArrayList<>();

    public PortfolioService() {
        portfolios.add(
            new Portfolio(
                UUID.randomUUID(),
                "Tech Growth",
                "Long-term growth focused tech portfolio",
                List.of(
                    new Position("AAPL", 50, BigDecimal.valueOf(185.23)),
                    new Position("MSFT", 30, BigDecimal.valueOf(275.00))
                )
            )
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

    public Portfolio createPortfolio(Portfolio portfolio) {
        if (portfolio.getId() == null) {
            portfolio.setId(UUID.randomUUID());
        }
        if (portfolio.getPositions() == null) {
            portfolio.setPositions(List.of());
        }
        portfolios.add(portfolio);
        return portfolio;
}
}



