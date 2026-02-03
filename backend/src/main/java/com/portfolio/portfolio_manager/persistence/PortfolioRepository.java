package com.portfolio.portfolio_manager.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/*
This is the JPA repository interface for PortfolioEntity.
It extends JpaRepository to provide CRUD operations for PortfolioEntity objects.
"Repository" interfaces in Spring Data JPA are used to interact with the database.
 */

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, UUID> {

    

    
}
