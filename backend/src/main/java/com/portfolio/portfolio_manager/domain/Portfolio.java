package com.portfolio.portfolio_manager.domain;
import java.util.UUID; 

public class Portfolio {

    private UUID id;
    private String name;
    private String owner; 

    public Portfolio(UUID id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
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
    
}
