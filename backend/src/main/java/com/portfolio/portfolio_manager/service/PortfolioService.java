package com.portfolio.portfolio_manager.service;

import com.portfolio.portfolio_manager.domain.Portfolio;
import com.portfolio.portfolio_manager.domain.Position; 
import org.springframework.stereotype.Service; 
import com.portfolio.portfolio_manager.persistence.PortfolioEntity;
import com.portfolio.portfolio_manager.persistence.PositionEntity;
import com.portfolio.portfolio_manager.persistence.PortfolioRepository;
import com.portfolio.portfolio_manager.dto.PortfolioCreateRequest;
import com.portfolio.portfolio_manager.dto.PortfolioUpdateRequest;
import com.portfolio.portfolio_manager.dto.PortfolioResponse;
import com.portfolio.portfolio_manager.dto.PositionResponse;


import java.math.BigDecimal;
import java.util.*;

/*

    This is the service class for Portfolio.
    It contains business logic for managing portfolios.

    "Service" classes in Spring are used to encapsulate the business logic of the application.
    Called by controllers to perform operations related to the domain, such as creating, retrieving, updating, or deleting portfolios.

*/

@Service
public class PortfolioService {

    private final PortfolioRepository repo;

    /*
    This function is the constructor for PortfolioService.
    It initializes the PortfolioService with a PortfolioRepository instance.
    */ 
    public PortfolioService(PortfolioRepository repo) {
        this.repo = repo;
    }


    /*
    This function retrieves all portfolios from the repository, converts them to domain objects, and returns them as a list.
    */
    public List<PortfolioResponse> getPortfolios() {
        return repo.findAll().stream().map(this::toDomain).map(this::toResponse).toList();
    }


    /*
    This function converts a PortfolioEntity to a Portfolio domain object.
    */
    public Portfolio toDomain(PortfolioEntity entity) { 
        List<Position> positions = entity.getPositions().stream().map(p -> new Position(
            p.getId(),           
            p.getSymbol(), 
            p.getQuantity(),
            p.getAvgPrice()
        )).toList(); 

        return new Portfolio(entity.getId(), entity.getName(), entity.getOwner(), positions);
    }

    /*
    This function converts a Portfolio domain object to a PortfolioEntity.
    */
    private PortfolioEntity toEntity(Portfolio d) {
        PortfolioEntity e = new PortfolioEntity(); 
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
    return e; 
}

/*
This function retrieves a portfolio by its ID, converts it to a domain object, and returns it wrapped in an Optional.
*/
    public Optional<PortfolioResponse> getPortfolioById(UUID id) {
        return repo.findById(id).map(this::toDomain).map(this::toResponse);
    }


    /*
    This function creates a new portfolio by saving it to the repository and returns the saved portfolio as a domain object.
    */
    public PortfolioResponse createPortfolio(PortfolioCreateRequest request) {
        Portfolio portfolio = new Portfolio(
            null,
            request.name(),
            request.owner(),
            new ArrayList<>()
        );
        PortfolioEntity saved = repo.save(toEntity(portfolio));
        return toResponse(toDomain(saved));
    }


    /*
    This function deletes a portfolio by its ID. 
    It returns true if the portfolio existed and was deleted, or false if the portfolio did not exist.
    */
    public boolean deletePortfolio(UUID id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


   /*
    This function updates an existing portfolio identified by its ID with the provided update request data.
    It returns an Optional containing the updated portfolio response if the portfolio exists and was updated, or
    an empty Optional if the portfolio does not exist.
    */

    public Optional<PortfolioResponse> updatePortfolio(UUID id, PortfolioUpdateRequest request) {
        return repo.findById(id).map(portfolioEntity -> {
            portfolioEntity.setName(request.getName());
            portfolioEntity.setOwner(request.getOwner());
            PortfolioEntity updated = repo.save(portfolioEntity);
            return toResponse(toDomain(updated));
        });
    }

    /*
    This function adds a position to a portfolio identified by portfolioId. 
    It returns an Optional containing the updated portfolio response if the portfolio exists and the position was added, or an empty Optional if the portfolio does not exist.
    */
    public Optional<PortfolioResponse> addPosition(UUID portfolioId, Position position) {
        return repo.findById(portfolioId).map(portfolioEntity -> {
            
            PositionEntity positionEntity = new PositionEntity();
            positionEntity.setSymbol(position.getSymbol());
            positionEntity.setQuantity(position.getQuantity());
            positionEntity.setAvgPrice(position.getAvgPrice());
            positionEntity.setPortfolio(portfolioEntity); 
            portfolioEntity.getPositions().add(positionEntity);

            PortfolioEntity updated = repo.save(portfolioEntity);
            
            return toResponse(toDomain(updated));
        });
    }

    /*
    This function retrieves the list of positions for a given portfolio identified by portfolioId. 
    It returns an Optional containing the list of positions if the portfolio exists, or an empty Optional if it does not.
    */
    public Optional<List<Position>> getPositions(UUID portfolioId) {
        return repo.findById(portfolioId).map(this::toDomain).map(Portfolio::getPositions); 
    }   


    /*
    This function deletes a position identified by positionId from a portfolio identified by portfolioId. 
    It returns true if the position was found and deleted, or false if the portfolio or position
    */
    public boolean deletePosition(UUID portfolioId, UUID positionId){
        Optional<PortfolioEntity> opt = repo.findById(portfolioId); 
        if (opt.isEmpty()) return false; 

        PortfolioEntity portfolio = opt.get(); 

        boolean removed = portfolio.getPositions().removeIf(p -> p.getId().equals(positionId)); 
        if (!removed) {
            return false; 
        }

        repo.save(portfolio); 
        return true; 

    }

    /*
    This function converts a Portfolio domain object to a PortfolioResponse DTO.
    */
    private PortfolioResponse toResponse(Portfolio portfolio){
        List<PositionResponse> positions = portfolio.getPositions().stream()
        .map(p -> new PositionResponse(
            p.getId(),
            p.getSymbol(),
            p.getQuantity(),
            p.getAvgPrice()
        )).toList();

        return new PortfolioResponse(
            portfolio.getId(),
            portfolio.getName(),
            portfolio.getOwner(),
            positions
        );
    }
}







