# Portfolio-Manager
Java-based back-end system that ingests trades, maintains portfolio positions, prices holdings, and exposes reporting/risk-style endpoints with an event-driven workflow. 

# Internal Portfolio Management for an Investment Firm
This application simulates a backend portfolio management system used by an investment firm to track client investment portfolios and their underlying positions. 

In a real financial institutioon, portfolio data is not managed through spreadsheets or ad-hoc scripts. Instead they rely on centralized backend services that expose APIs used by internal tools, reporting systems, and downstream analytics platforms 

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
   - Internal tools request portfolio data via rest endpoints
   - service returns structured portfolio and position data for use in dashboards, reporting systems, or batch progressing jobs.
  
 4. Persistence and data integrity
    - portfolio and position data are stored in a relational database
    - JPA entity relationships enforce referential integrity between portfolios and positions 
