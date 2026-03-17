package com.portfolio.portfolio_manager.dto;

import java.math.BigDecimal;
import java.util.UUID;

/*

    This is the DTO record for PositionResponse.
    PositionResponse is used to transfer position data to clients.

*/

public record PositionResponse(
    UUID id, 
    String symbol, 
    Integer quantity, 
    BigDecimal avgPrice, 
    BigDecimal costBasis, 
    BigDecimal currentPrice, 
    BigDecimal marketValue,
    BigDecimal unrealizedGainLoss
 )  {}
