package com.portfolio.portfolio_manager.service;

import com.portfolio.portfolio_manager.domain.Portfolio;
import com.portfolio.portfolio_manager.domain.PortfolioSnapshot;
import com.portfolio.portfolio_manager.domain.Position; 
import org.springframework.stereotype.Service; 
import com.portfolio.portfolio_manager.persistence.PortfolioEntity;
import com.portfolio.portfolio_manager.persistence.PositionEntity;
import com.portfolio.portfolio_manager.persistence.PortfolioRepository;
import com.portfolio.portfolio_manager.persistence.PortfolioSnapshotEntity;
import com.portfolio.portfolio_manager.persistence.PortfolioSnapshotRepository;
import com.portfolio.portfolio_manager.dto.PortfolioCreateRequest;
import com.portfolio.portfolio_manager.dto.PortfolioUpdateRequest;
import com.portfolio.portfolio_manager.dto.PortfolioSnapshotResponse;
import com.portfolio.portfolio_manager.dto.PortfolioResponse;
import com.portfolio.portfolio_manager.dto.PositionResponse;
import com.portfolio.portfolio_manager.dto.PositionUpdateRequest;
import com.portfolio.portfolio_manager.market.MarketDataService;

import java.math.BigDecimal;
import java.time.Instant;
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
    private final PortfolioSnapshotRepository snapshotRepo; 
    private final MarketDataService marketDataService;

    /*
    This function is the constructor for PortfolioService.
    It initializes the PortfolioService with a PortfolioRepository instance, a PortfolioSnapshotRepository instance, and a MarketDataService instance.
    */ 
    public PortfolioService(PortfolioRepository repo, PortfolioSnapshotRepository snapshotRepo, MarketDataService marketDataService) {
        this.repo = repo;
        this.snapshotRepo = snapshotRepo;
        this.marketDataService = marketDataService;
    }


    /*
    This function retrieves all portfolios from the repository, converts them to domain objects, and returns them as a list.
    */
    public List<PortfolioResponse> getPortfolios() {
        return repo.findAll().stream().map(this::toDomain).map(this::toResponse).toList();
    }


    /*
    This function converts a PortfolioEntity to a Portfolio domain object.

        -Updated 3/17/2026: Added mapping for currentPrice in PositionEntity to Position domain object, as currentPrice is now a required field in the Position domain model and is needed for calculating the cost basis and total cost basis of the portfolio.
    */
    public Portfolio toDomain(PortfolioEntity entity) { 
        List<Position> positions = entity.getPositions().stream().map(p -> new Position(
            p.getId(),           
            p.getSymbol(), 
            p.getQuantity(),
            p.getAvgPrice(), 
            p.getCurrentPrice()
        )).toList(); 

        return new Portfolio(entity.getId(), entity.getName(), entity.getOwner(), positions);
    }

    /*
    This function converts a Portfolio domain object to a PortfolioEntity.

        -Updated 3/17/2026: Added mapping for currentPrice in Position domain object to PositionEntity, as currentPrice is now a required field in the Position domain model and is needed for calculating the cost basis and total cost basis of the portfolio.
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
                pe.setCurrentPrice(p.getCurrentPrice());
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
    
    -Updated 3/17/2026: Added setting of currentPrice in PositionEntity when adding a new position, as currentPrice is now a required field in the Position domain model and 
                        is needed for calculating the cost basis and total cost basis of the portfolio.

    -Updated 4/8/2026: Added fallback to avg price if current price is not provided when adding a new position, as currentPrice is now a required field in the Position domain 
                       model and is needed for calculating the cost basis and total cost basis of the portfolio. 
                    .
     */
    
    
    public Optional<PortfolioResponse> addPosition(UUID portfolioId, Position position) {
        Optional<PortfolioEntity> opt = repo.findById(portfolioId);
        if (opt.isEmpty()) {
            return Optional.empty();
        }

        PortfolioEntity portfolioEntity = opt.get();

        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setSymbol(position.getSymbol());
        positionEntity.setQuantity(position.getQuantity());
        positionEntity.setAvgPrice(position.getAvgPrice());
        positionEntity.setCurrentPrice(
            position.getCurrentPrice() != null
                ? position.getCurrentPrice()
                : position.getAvgPrice()
        );
        positionEntity.setPortfolio(portfolioEntity);

        portfolioEntity.getPositions().add(positionEntity);

        PortfolioEntity saved = repo.save(portfolioEntity);

        return Optional.of(toResponse(toDomain(saved)));
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
    It returns true if the position was found and deleted, or false if the portfolio or position does not exist.
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
    
    -Updated 3/17/2026: Added costBasis to PositionResponse mapping and totalCostBasis and positionCount to PortfolioResponse, 
                        as these fields are now needed on the frontend for displaying the cost basis of each position and the 
                        total cost basis of the portfolio, as well as the number of positions in the portfolio.
    */
    private PortfolioResponse toResponse(Portfolio portfolio){
        List<PositionResponse> positions = portfolio.getPositions().stream()
        .map(p -> new PositionResponse(
            p.getId(),
            p.getSymbol(),
            p.getQuantity(),
            p.getAvgPrice(), 
            p.getCostBasis(), 
            p.getCurrentPrice(),
            p.getMarketValue(),
            p.getUnrealizedGainLoss()
        )).toList();

        return new PortfolioResponse(
            portfolio.getId(),
            portfolio.getName(),
            portfolio.getOwner(),
            positions, 
            portfolio.getTotalCostBasis(),
            portfolio.getTotalMarketValue(),
            portfolio.getTotalUnrealizedGainLoss(),
            portfolio.getTotalReturnPercentage(),
            portfolio.getPositionCount()    
        );
    }

    /*
    This function updates a position identified by positionId in a portfolio identified by portfolioId with the provided update request data.
    It returns an Optional containing the updated portfolio response if the portfolio and position exist and were updated, or an empty Optional if the portfolio or position does not exist.

    -Updated 3/4/2026: Removed position parameter from the function signature, as it is not needed for updating the position. 
                       The position to be updated is identified by the positionId parameter, 
                       and the new data for the position is provided in the PositionUpdateRequest object.

    -Updated 3/17/2026: Added updating of currentPrice in PositionEntity when updating a position, as currentPrice is now a required field in the Position domain model and 
                        is needed for calculating the cost basis and total cost basis of the portfolio.
    
    -Updated 4/10/2026: Updated the updatePosition method to only set the currentPrice on the position if the PositionUpdateRequest contains a non-null currentPrice value; 
                        if the request does not supply a currentPrice, the existing currentPrice on the position is preserved.   
    */

    public Optional<PortfolioResponse> updatePosition(UUID portfolioId, UUID positionId, PositionUpdateRequest request) {
        Optional<PortfolioEntity> opt = repo.findById(portfolioId);
        if (opt.isEmpty()) {
            return Optional.empty();
        }

        PortfolioEntity portfolio = opt.get();

        PositionEntity positionEntity = portfolio.getPositions().stream()
                .filter(p -> p.getId().equals(positionId))
                .findFirst()
                .orElse(null);

        if (positionEntity == null) {
            return Optional.empty();
        }

        positionEntity.setQuantity(request.quantity());
        positionEntity.setAvgPrice(request.avgPrice());
        positionEntity.setCurrentPrice(request.currentPrice() != null ? request.currentPrice() : positionEntity.getCurrentPrice());

        PortfolioEntity saved = repo.save(portfolio);

        return Optional.of(toResponse(toDomain(saved)));
    }

    /*
    This function refreshes the current prices of all positions in a portfolio identified by portfolioId.   
    It returns an Optional containing the updated portfolio response if the portfolio exists and the 
    prices were refreshed, or an empty Optional if the portfolio does not exist.    
    */
    public Optional<PortfolioResponse> refreshPrices(UUID portfolioId){ 
        Optional<PortfolioEntity> opt = repo.findById(portfolioId); 
        if (opt.isEmpty()) { 
            return Optional.empty(); 
        }

        PortfolioEntity portfolio = opt.get(); 

        for (PositionEntity position : portfolio.getPositions()) { 
            BigDecimal livePrice = marketDataService.getCurrentPrice(position.getSymbol());
            position.setCurrentPrice(livePrice);
        }

        PortfolioEntity saved = repo.save(portfolio); 
        return Optional.of(toResponse(toDomain(saved)));
        
    }

    /*
    This function creates a snapshot of the current state of a portfolio identified by portfolioId.
    It returns an Optional containing the created PortfolioSnapshotResponse if the portfolio exists and the snapshot was created, or an empty Optional if the portfolio does not exist.
    */

    public Optional<PortfolioSnapshotResponse> createSnapshot(UUID portfolioId) {
        return repo.findById(portfolioId).map(entity -> { 
            Portfolio portfolio = toDomain(entity); 

            PortfolioSnapshotEntity snapshotEntity = new PortfolioSnapshotEntity();
            snapshotEntity.setPortfolioId(portfolio.getId());
            snapshotEntity.setTimestamp(Instant.now());
            snapshotEntity.setTotalValue(portfolio.getTotalMarketValue());
            snapshotEntity.setTotalCostBasis(portfolio.getTotalCostBasis());
            snapshotEntity.setTotalUnrealizedGainLoss(portfolio.getTotalUnrealizedGainLoss());

            PortfolioSnapshotEntity savedSnapshot = snapshotRepo.save(snapshotEntity);

            return new PortfolioSnapshotResponse(
                savedSnapshot.getId(),
                savedSnapshot.getPortfolioId(),
                savedSnapshot.getTimestamp(),
                savedSnapshot.getTotalMarketValue(),
                savedSnapshot.getTotalCostBasis(),
                savedSnapshot.getTotalUnrealizedGainLoss()
            );
        });
    }

    private PortfolioSnapshotResponse toSnapshotResponse(PortfolioSnapshotEntity s) {
    return new PortfolioSnapshotResponse(
        s.getId(),
        s.getPortfolioId(),
        s.getTimestamp(),
        s.getTotalMarketValue(),
        s.getTotalCostBasis(),
        s.getTotalUnrealizedGainLoss()
        );
    }

    public List<PortfolioSnapshotResponse> getSnapshotsForPortfolio(UUID portfolioId) {
        return snapshotRepo.findByPortfolioIdOrderByTimestampAsc(portfolioId).stream()
            .map(this::toSnapshotResponse)
            .toList();
    }
}



