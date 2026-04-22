package com.portfolio.portfolio_manager.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfolio_manager.dto.QuoteResponse;
import com.portfolio.portfolio_manager.market.MarketDataService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/quotes")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/{symbol}")
    public QuoteResponse getQuote(@PathVariable String symbol) {
        BigDecimal price = marketDataService.getCurrentPrice(symbol);
        return new QuoteResponse(symbol.toUpperCase(), price);
    }
}