package com.portfolio.portfolio_manager.domain;

import java.math.BigDecimal;

public class Position {

    private String symbol;
    private BigDecimal quantity;
    private BigDecimal avgPrice;

    public Position(String symbol, BigDecimal quantity, BigDecimal avgPrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.avgPrice = avgPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }
    
}
