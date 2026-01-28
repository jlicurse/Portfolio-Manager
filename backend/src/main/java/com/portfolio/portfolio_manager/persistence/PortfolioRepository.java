package com.portfolio.portfolio_manager.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, UUID> {

    

    
}
