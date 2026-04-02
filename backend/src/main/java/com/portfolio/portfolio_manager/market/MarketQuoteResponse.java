package com.portfolio.portfolio_manager.market;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MarketQuoteResponse (
    @JsonProperty("c") BigDecimal currentPrice

)
    
{}
