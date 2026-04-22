function PortfolioList({ portfolios, onSelectPortfolio, selectedPortfolioId }) {
  return (
    <section className="panel sidebar">
      <h2>Portfolios</h2>

      {portfolios.length === 0 ? (
        <p>No portfolios found.</p>
      ) : (
        <ul className="portfolio-list">
          {portfolios.map((portfolio) => (
            <li key={portfolio.id}>
              <button
                className={`portfolio-button ${
                  selectedPortfolioId === portfolio.id ? "selected" : ""
                }`}
                onClick={() => onSelectPortfolio(portfolio.id)}
              >
                <div className="portfolio-name">{portfolio.name}</div>
                <div className="portfolio-meta">
                  {portfolio.owner} • Positions: {portfolio.positionCount}
                </div>
              </button>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}

export default PortfolioList;