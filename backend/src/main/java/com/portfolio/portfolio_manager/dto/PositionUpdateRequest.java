package com.portfolio.portfolio_manager.dto;

import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.DecimalMin; 
import java.math.BigDecimal;

/* DTO for updating a position */

public record PositionUpdateRequest (
    
    @NotNull(message = "quantity is required")
    BigDecimal quantity,

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "average price must be greater than 0")
    BigDecimal price
)
{}