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
    
}
