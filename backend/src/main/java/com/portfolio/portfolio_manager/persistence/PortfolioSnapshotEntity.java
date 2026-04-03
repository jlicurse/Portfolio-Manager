package com.portfolio.portfolio_manager.persistence;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

/*

JPA entity representing a snapshot of a portfolio at a specific point in time. 

*/

@Entity
@Table(name = "portfolio_snapshots")
public class PortfolioSnapshotEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID portfolioId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal totalValue;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal totalCostBasis;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal totalUnrealizedGainLoss;

    @Version 
    private Long version;

    public PortfolioSnapshotEntity() {}

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }   
}
