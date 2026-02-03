package com.portfolio.portfolio_manager.domain;
import java.util.UUID; 
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
        this.positions = positions;
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

    
}
