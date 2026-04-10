package com.portfolio.portfolio_manager.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PortfolioSnapshotResponse(
    UUID id,
    UUID portfolioId,
    Instant timestamp,
    BigDecimal totalValue,
    BigDecimal totalCostBasis,
    BigDecimal totalUnrealizedGainLoss
    
) {}

