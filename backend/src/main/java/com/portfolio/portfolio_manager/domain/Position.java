package com.portfolio.portfolio_manager.domain;

import java.math.BigDecimal;
import java.util.UUID;

/*
    This is the domain model class for Position.
    Position represents a financial position within a portfolio.

    "Domain" classes are plain Java objects that represent the core business entities in the application.
*/

public class Position {

    private UUID id;    
    private String symbol;
    private Integer quantity;
    private BigDecimal avgPrice;

    public Position(UUID id, String symbol, Integer quantity, BigDecimal avgPrice) {
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
        this.avgPrice = avgPrice;
    }

     public UUID getId() {
        return id; //was returning null for some reason and blew up the tests
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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    
}
