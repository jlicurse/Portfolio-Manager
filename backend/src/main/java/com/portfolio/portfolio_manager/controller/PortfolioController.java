package com.portfolio.portfolio_manager.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import com.portfolio.portfolio_manager.domain.Portfolio;
import com.portfolio.portfolio_manager.domain.Position;
import com.portfolio.portfolio_manager.service.PortfolioService;

import java.util.UUID;
import java.util.List;

/*

    This is the controller class for Portfolio.
    It handles HTTP requests related to portfolios.

    "Controller" classes in Spring are used to define RESTful endpoints.
    They receive HTTP requests, delegate processing to service classes, and return HTTP responses.

*/
  

@RestController
public class PortfolioController {
    
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/api/portfolios")
    public List<Portfolio> getPortfolios() {
        return portfolioService.getPortfolios();

    }

    @GetMapping("/api/portfolios/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable UUID id) {
        return portfolioService.getPortfolioById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/portfolios")
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody Portfolio portfolio) {
        Portfolio createdPortfolio = portfolioService.createPortfolio(portfolio);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPortfolio);
    }

    @DeleteMapping("/api/portfolios/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable UUID id) {
        boolean deleted = portfolioService.deletePortfolio(id); 

        if (deleted) {
            return ResponseEntity.noContent().build(); //204 No Content
        } else {
            return ResponseEntity.notFound().build(); //404 Not Found
        }
    }
    
}
    

