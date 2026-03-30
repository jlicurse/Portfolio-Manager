# Portfolio-Manager
Java-based back-end portfolio management system that ingests trades, maintains portfolio positions, integrates live market data, and calculates real-time portfolio valuations through RESTful APIs. 

The system simulates infrastructure used by financial institutions to track client portfolios, update market pirces, and expose data for reporting, analytics, and risk evaluation. 

# Internal Portfolio Management for an Investment Firm
This application simulates a backend portfolio management system used by an investment firm to track client investment portfolios and their underlying positions. 

In a real financial institution, portfolio data is not managed through spreadsheets or ad-hoc scripts. Instead they rely on centralized backend services that expose APIs used by internal tools, reporting systems, and downstream analytics platforms 

Each client portfolio contains: 
- a unique portfolio ID
- Client or portfolio owner info
- collection of investment positions (stocks, ETFs, or other assets)

Portfolio managers and internal systems need to: 
- create new portfolios when onboarding clients
- add, update, or remove positions within a portfolio
- Retrieve portfolio data for reporting, risk analysis, or valuation engines
- delete portfolios when accounts are closed

# How App Works: 
1. Portfolio Creation
   - portfolio manager or onboarding system sends a request to create a new portfolio.
   -  the backend persists the portfolio and assigns it a unique identifier

2. Position Management
   - positions are added to portfolios as trades occur
   - each position represents ownership in a specific asset and is associated with exactly one portfolio.
  
3. Data Retrieval
   - Internal tools request portfolio data via RESTful endpoints
   - service returns structured portfolio and position data for use in dashboards, reporting systems, or batch processing jobs.
  
 4. Persistence and data integrity
    - portfolio and position data are stored in a relational database
    - JPA entity relationships enforce referential integrity between portfolios and positions

5. Portfolio Valuation
   - the system calculates key financial metrics for each position:
        - Cost Basis = avgPrice x quantity
        - Market Value = currentPrice x quantity
        - Unrealized gain/loss = marketValue - costBasis
   - At the portfolio level:
        - Total Cost Basis = sum of all positions
        - Position Count = total number of holdings
   - These calculations are performed dynamically and updated whenever market prices are refreshed 

# Live Market Data 
The application integrates with an external market data provider (Finnhub API) to retrieve real-time asset prices. 

A dedicated endpoint allows portfolios to refresh current market prices:
- POST /api/portfolios/{id}/refresh-prices

When triggered, the system: 
- Fetches live prices for each position based on its symbol
- Updates stored current prices
- Recalculates market value and unrealized gain/loss
- Returns update portfolio metrics.

# Key API Endpoints 
- POST /api/portfolios → create portfolio
- GET /api/portfolios → list portfolios
- GET /api/portfolios/{id} → retrieve portfolio
- PUT /api/portfolios/{id} → update portfolio
- DELETE /api/portfolios/{id} → delete portfolio
- POST /api/portfolios/{id}/positions → add position
- PUT /api/portfolios/{id}/positions/{positionId} → update position
- POST /api/portfolios/{id}/refresh-prices → update live market prices

