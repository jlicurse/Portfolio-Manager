package com.portfolio.portfolio_manager.dto;

import jakarta.validation.constraints.NotBlank;
/*

    This is the DTO class for updating a Portfolio.
    It can contain fields that are allowed to be updated in an existing portfolio.

    "DTO" (Data Transfer Object) classes are used to transfer data between different layers of an application.
    They often represent the data structure expected in API requests or responses.

*/

public class PortfolioUpdateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String owner;


    public PortfolioUpdateRequest() {}

    public PortfolioUpdateRequest(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
}
