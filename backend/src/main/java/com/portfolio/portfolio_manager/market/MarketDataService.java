package com.portfolio.portfolio_manager.market;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

/*

This class is the service layer responsible for fetching market data from Finnhub API. 
It uses Spring's RestClient to make HTTP requests to the API and retrieves the current 
price of a given stock symbol.

*/

@Service 
public class MarketDataService {

    private final RestClient restClient;
    private final String apiKey;

    public MarketDataService(RestClient.Builder restClientBuilder, @Value("${finnhub.api.key}") String apiKey) {
        this.restClient = restClientBuilder.baseUrl("https://finnhub.io/api/v1").build();
        this.apiKey = apiKey;

}

    public BigDecimal getCurrentPrice(String symbol) {
        MarketQuoteResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/quote")
                        .queryParam("symbol", symbol)
                        .queryParam("token", apiKey)
                        .build())
                .retrieve()
                .body(MarketQuoteResponse.class); 

        if (response == null || response.currentPrice() == null) {
            throw new RuntimeException("No market price returned for symbol: " + symbol);
        }

        return response.currentPrice();
    }


    
}
