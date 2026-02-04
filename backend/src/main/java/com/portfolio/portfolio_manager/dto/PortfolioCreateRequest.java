package com.portfolio.portfolio_manager.dto;

import jakarta.validation.constraints.NotBlank; 

/*

    This is the DTO class for creating a Portfolio.
    It contains the necessary fields to create a new portfolio.

    "DTO" (Data Transfer Object) classes are used to transfer data between different layers of an application.
    They often represent the data structure expected in API requests or responses.

*/


public record PortfolioCreateRequest(
    @NotBlank(message = "name is required") String name,
    @NotBlank(message = "owner is required") String owner
){}

    

