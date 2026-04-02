package com.portfolio.portfolio_manager.dto;

import java.math.BigDecimal;
import java.util.List; 
import java.util.UUID; 


/*

    This is the DTO record for PortfolioResponse.
    PortfolioResponse is used to transfer portfolio data, including its positions, to clients.

    -Updated to carry total cost basis and position count for easier access on the frontend.
*/

public record PortfolioResponse (
    UUID id,
    String name,
    String owner,
    List<PositionResponse> positions, 
    BigDecimal totalCostBasis, 
    BigDecimal totalMarketValue, 
    BigDecimal totalUnrealizedGainLoss,
    BigDecimal totalReturnPercentage, 
    int positionCount
) {}
    

