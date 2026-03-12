package com.portfolio.portfolio_manager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/* DTO for updating a position */
public record PositionUpdateRequest(

    @NotNull(message = "quantity is required")
    Integer quantity,

    @NotNull(message = "avgPrice is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "avgPrice must be greater than 0")
    BigDecimal avgPrice
) {}