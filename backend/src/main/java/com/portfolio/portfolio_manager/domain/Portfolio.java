package com.portfolio.portfolio_manager.domain;
import java.util.UUID; 
import java.util.List;

public class Portfolio {

    private UUID id;
    private String name;
    private String owner; 
    private List<Position> positions;


    public Portfolio(UUID id, String name, String owner, List<Position> positions) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.positions = positions;
    }

    public UUID getId() {
        return id;
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
    
}
