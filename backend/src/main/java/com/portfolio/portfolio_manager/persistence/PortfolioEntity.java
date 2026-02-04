package com.portfolio.portfolio_manager.persistence;

import jakarta.persistence.*; 
import java.util.*; 

@Entity
@Table(name = "portfolios")
public class PortfolioEntity {

/*
    These are the JPA entity definitions for PortfolioEntity.
    PortfolioEntity represents a financial portfolio.

    "Entity" classes use JPA annotations to define the mapping between the Java objects and the database tables.

*/

    @Id
    @GeneratedValue
    private UUID id; 
    private String name;
    private String owner; 

    @Version
    private Long version;


    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<PositionEntity> positions = new ArrayList<>();

    public PortfolioEntity() {}

    public UUID getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public List<PositionEntity> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionEntity> positions) {
        this.positions = positions;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public void setOwner(String owner) {
        this.owner = owner;
    }

}