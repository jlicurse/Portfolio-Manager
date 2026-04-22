import PositionTable from "./PositionTable";
import AddPositionForm from "./AddPositionForm";

function PortfolioDetail({ portfolio, onPositionAdded }) {
  const formatCurrency = (value) =>
    `$${Number(value ?? 0).toLocaleString(undefined, {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    })}`;

  const formatPercent = (value) =>
    `${Number(value ?? 0).toLocaleString(undefined, {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    })}%`;

  if (!portfolio) {
    return (
      <section className="panel detail-panel">
        <h2>Portfolio Details</h2>
        <p>Select a portfolio to view details.</p>
      </section>
    );
  }

  return (
    <section className="panel detail-panel">
      <h2>{portfolio.name}</h2>

      <div className="summary-grid">
        <div className="summary-card">
          <div className="summary-label">Owner</div>
          <div className="summary-value">{portfolio.owner}</div>
        </div>

        <div className="summary-card">
          <div className="summary-label">Positions</div>
          <div className="summary-value">{portfolio.positionCount}</div>
        </div>

        <div className="summary-card">
          <div className="summary-label">Total Cost Basis</div>
          <div className="summary-value">
            {formatCurrency(portfolio.totalCostBasis)}
          </div>
        </div>

        <div className="summary-card">
          <div className="summary-label">Total Market Value</div>
          <div className="summary-value">
            {formatCurrency(portfolio.totalMarketValue)}
          </div>
        </div>

        <div className="summary-card">
          <div className="summary-label">Unrealized Gain/Loss</div>
          <div
            className="summary-value"
            style={{
              color:
                Number(portfolio.totalUnrealizedGainLoss ?? 0) >= 0
                  ? "limegreen"
                  : "red"
            }}
          >
            {formatCurrency(portfolio.totalUnrealizedGainLoss)}
          </div>
        </div>

        <div className="summary-card">
          <div className="summary-label">Return</div>
          <div
            className="summary-value"
            style={{
              color:
                Number(portfolio.totalReturnPercentage ?? 0) >= 0
                  ? "limegreen"
                  : "red"
            }}
          >
            {formatPercent(portfolio.totalReturnPercentage)}
          </div>
        </div>
      </div>

      <AddPositionForm
        portfolioId={portfolio.id}
        onAdded={onPositionAdded}
      />

      <h3>Positions</h3>
      <PositionTable positions={portfolio.positions} />
    </section>
  );
}

export default PortfolioDetail;