package com.portfolio.portfolio_manager.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/* 

This class represents a snapshot of a portfolio at a specific point in time. 
Used for historical tracking and performance analysis. 

*/

public class PortfolioSnapshot {
    
    private UUID id;
    private UUID portfolioId;
    private Instant timestamp;
    private BigDecimal totalValue;
    private BigDecimal totalCostBasis;
    private BigDecimal totalUnrealizedGainLoss;

    public PortfolioSnapshot(UUID id, UUID portfolioId, Instant timestamp, BigDecimal totalValue, BigDecimal totalCostBasis, BigDecimal totalUnrealizedGainLoss) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.timestamp = timestamp;
        this.totalValue = totalValue;
        this.totalCostBasis = totalCostBasis;
        this.totalUnrealizedGainLoss = totalUnrealizedGainLoss;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(UUID portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getTotalCostBasis() {
        return totalCostBasis;
    }

    public void setTotalCostBasis(BigDecimal totalCostBasis) {
        this.totalCostBasis = totalCostBasis;
    }

    public BigDecimal getTotalUnrealizedGainLoss() {
        return totalUnrealizedGainLoss;
    }

    public void setTotalUnrealizedGainLoss(BigDecimal totalUnrealizedGainLoss) {
        this.totalUnrealizedGainLoss = totalUnrealizedGainLoss;
    }

        
}
