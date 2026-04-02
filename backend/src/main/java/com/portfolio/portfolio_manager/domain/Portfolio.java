package com.portfolio.portfolio_manager.domain;
import java.util.UUID;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
/*
    This is the domain model class for Portfolio.
    Portfolio represents a collection of financial positions owned by an individual or entity.

    "Domain" classes are plain Java objects that represent the core business entities in the application.
*/

public class Portfolio {

    private UUID id;
    private String name;
    private String owner; 
    private List<Position> positions;


    public Portfolio(UUID id, String name, String owner, List<Position> positions) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.positions = positions != null ? positions : new ArrayList<>(); // Ensure positions is never null
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getTotalCostBasis() {
        if (positions == null || positions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return positions.stream()
                        .map(Position::getCostBasis)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getPositionCount() {
            return positions == null ? 0 : positions.size();
    }

    public BigDecimal getTotalMarketValue() { 
        if (positions == null || positions.isEmpty()) { 
            return BigDecimal.ZERO;
        }

        return positions.stream()
        .map(Position::getMarketValue)
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalUnrealizedGainLoss() { 
        if (positions == null || positions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return positions.stream()
                        .map(Position::getUnrealizedGainLoss)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalReturnPercentage() { 
        BigDecimal totalCostBasis = getTotalCostBasis(); 

        if (totalCostBasis.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // Avoid division by zero
        }

        return getTotalUnrealizedGainLoss().divide(totalCostBasis, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

    }
}
