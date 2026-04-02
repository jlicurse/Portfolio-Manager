package com.portfolio.portfolio_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/* 

This class is a configuration class for the RestClient bean used in the application.
It defines a RestClient.Builder bean that can be injected into other components, 
such as the MarketDataService, to create RestClient instances for making HTTP 
requests to external APIs.

*/


@Configuration
public class RestClientConfig {

    @Bean
    public RestClient.Builder restClient() {
        return RestClient.builder();
    }



    
}
