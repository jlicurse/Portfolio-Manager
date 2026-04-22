function PositionTable({ positions }) {
  if (!positions || positions.length === 0) {
    return <p>This portfolio has no positions yet.</p>;
  }

  return (
    <table className="positions-table">
      <thead>
        <tr>
          <th>Symbol</th>
          <th>Quantity</th>
          <th>Avg Price</th>
          <th>Current Price</th>
          <th>Cost Basis</th>
          <th>Market Value</th>
          <th>Gain/Loss</th>
        </tr>
      </thead>
      <tbody>
        {positions.map((position) => (
          <tr key={position.id}>
            <td>{position.symbol}</td>
            <td>{position.quantity}</td>
            <td>${Number(position.avgPrice).toFixed(2)}</td>
            <td>${Number(position.currentPrice).toFixed(2)}</td>
            <td>${Number(position.costBasis).toFixed(2)}</td>
            <td>${Number(position.marketValue).toFixed(2)}</td>
            <td
                style={{
                 color:
                    position.unrealizedGainLoss >= 0 ? "limegreen" : "red",
                fontWeight: "bold"
                }}
>
                ${Number(position.unrealizedGainLoss).toFixed(2)}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default PositionTable;