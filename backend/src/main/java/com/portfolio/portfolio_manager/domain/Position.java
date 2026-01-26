package com.portfolio.portfolio_manager.domain;

import java.math.BigDecimal;

public class Position {

    private String symbol;
    private int quantity;
    private BigDecimal avgPrice;

    public Position(String symbol, int quantity, BigDecimal avgPrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.avgPrice = avgPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }
    
}
