package com.portfolio.portfolio_manager.dto;

import java.util.List; 
import java.util.UUID; 


/*

    This is the DTO record for PortfolioResponse.
    PortfolioResponse is used to transfer portfolio data, including its positions, to clients.

*/

public record PortfolioResponse (
    UUID id,
    String name,
    String owner,
    List<PositionResponse> positions
) {}
    

