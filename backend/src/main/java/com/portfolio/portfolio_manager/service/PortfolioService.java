package com.portfolio.portfolio_manager.service;

import com.portfolio.portfolio_manager.domain.Portfolio;
import com.portfolio.portfolio_manager.domain.Position; 
import org.springframework.stereotype.Service; 
import com.portfolio.portfolio_manager.persistence.PortfolioEntity;
import com.portfolio.portfolio_manager.persistence.PositionEntity;
import com.portfolio.portfolio_manager.persistence.PortfolioRepository;


import java.math.BigDecimal;
import java.util.*;

@Service
public class PortfolioService {

    private final PortfolioRepository repo;

    public PortfolioService(PortfolioRepository repo) {
        this.repo = repo;
    }

    public List<Portfolio> getPortfolios() {
        return repo.findAll().stream().map(this::toDomain).toList();
    }

    public Portfolio toDomain(PortfolioEntity entity) { 
        List<Position> positions = entity.getPositions().stream().map(p -> new Position(
            p.getSymbol(), 
            p.getQuantity(), 
            p.getAvgPrice()
        )).toList(); 

        return new Portfolio(entity.getId(), entity.getName(), entity.getOwner(), positions);
    }

    private PortfolioEntity toEntity(Portfolio d) {
        PortfolioEntity e = new PortfolioEntity(); 
        e.setId(d.getId());
        e.setName(d.getName());
        e.setOwner(d.getOwner());

        if (d.getPositions() != null) {
            for (Position p : d.getPositions()) {
                PositionEntity pe = new PositionEntity();
                pe.setSymbol(p.getSymbol());
                pe.setQuantity(p.getQuantity());
                pe.setAvgPrice(p.getAvgPrice());
                pe.setPortfolio(e); 
                e.getPositions().add(pe);
        }
    }

    public Optional<Portfolio> getPortfolioById(UUID id) {
        return repo.findById(id).map(this::toDomain);
    }

    public Portfolio createPortfolio(Portfolio portfolio) {
        PortfolioEntity saved = repo.save(toEntity(portfolio));
        return toDomain(saved);
    }

    public boolean deletePortfolio(UUID id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}



