package com.portfolio.portfolio_manager.dto;

import java.math.BigDecimal;

public record QuoteResponse(
    String symbol,
    BigDecimal price
) {}