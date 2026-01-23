package com.portfolio.portfolio_manager.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.portfolio.portfolio_manager.domain.Portfolio;
import com.portfolio.portfolio_manager.domain.Position;
import com.portfolio.portfolio_manager.service.PortfolioService;

import java.util.UUID;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;
  

@RestController
public class PortfolioController {
    
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

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

    @GetMapping("/api/portfolios/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable UUID id) {
        return portfolioService.getPortfolioById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    
}
    

