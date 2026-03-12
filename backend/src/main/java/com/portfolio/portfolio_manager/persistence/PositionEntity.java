package com.portfolio.portfolio_manager.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

/*
    These are the JPA entity definitions for PositionEntity.
    PositionEntity represents a financial position within a portfolio.

    "Entity" classes use JPA annotations to define the mapping between the Java objects and the database tables.

*/

@Entity
@Table(name = "positions")
public class PositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String symbol;
    private Integer quantity;

    @Column(precision = 19, scale = 4)
    private BigDecimal avgPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private PortfolioEntity portfolio;

    @Version 
    private Long version;

    public PositionEntity() {}

    public UUID getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public PortfolioEntity getPortfolio() {
        return portfolio;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public void setPortfolio(PortfolioEntity portfolio) {
        this.portfolio = portfolio;
    }

    



    
}
