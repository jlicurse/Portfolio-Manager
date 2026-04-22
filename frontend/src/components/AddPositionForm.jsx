import { useEffect, useState } from "react";
import { addPosition, getQuote } from "../api/portfolioApi";

function AddPositionForm({ portfolioId, onAdded }) {
  const [symbol, setSymbol] = useState("");
  const [quantity, setQuantity] = useState("");
  const [avgPrice, setAvgPrice] = useState("");
  const [currentPrice, setCurrentPrice] = useState("");
  const [error, setError] = useState("");
  const [loadingPrice, setLoadingPrice] = useState(false);
  const [quoteInfo, setQuoteInfo] = useState("");

  useEffect(() => {
    if (!symbol.trim()) {
      setCurrentPrice("");
      setQuoteInfo("");
      setError("");
      return;
    }

    const timeoutId = setTimeout(async () => {
      try {
        setError("");
        setLoadingPrice(true);

        const quote = await getQuote(symbol.trim());
        const price = String(quote.price);

        setCurrentPrice(price);
        setQuoteInfo(`${quote.symbol} → $${Number(quote.price).toLocaleString(undefined, {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2
        })}`);
      } catch (err) {
        setQuoteInfo("");
        setCurrentPrice("");
        setError("Failed to fetch price (invalid symbol or API issue)");
      } finally {
        setLoadingPrice(false);
      }
    }, 600);

    return () => clearTimeout(timeoutId);
  }, [symbol]);

  async function handleSubmit(e) {
    e.preventDefault();

    try {
      setError("");

      await addPosition(portfolioId, {
        symbol: symbol.trim().toUpperCase(),
        quantity: Number(quantity),
        avgPrice: Number(avgPrice),
        currentPrice: Number(currentPrice)
      });

      setSymbol("");
      setQuantity("");
      setAvgPrice("");
      setCurrentPrice("");
      setQuoteInfo("");

      onAdded();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ marginBottom: "20px" }}>
      <h3>Add Position</h3>

      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: "10px" }}>
          <label style={{ display: "block", marginBottom: "4px" }}>
            Symbol
          </label>

          <input
            placeholder="AAPL"
            value={symbol}
            onChange={(e) => setSymbol(e.target.value.toUpperCase())}
            required
          />

          <div style={{ marginTop: "6px", fontSize: "0.9rem", color: "#cbd5e1" }}>
            {loadingPrice
              ? "Looking up latest price..."
              : quoteInfo || "Enter a symbol to auto-load the current market price"}
          </div>
        </div>

        <div style={{ marginBottom: "10px" }}>
          <label style={{ display: "block", marginBottom: "4px" }}>
            Quantity
          </label>

          <input
            type="number"
            placeholder="10"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
            required
          />
        </div>

        <div style={{ marginBottom: "10px" }}>
          <label style={{ display: "block", marginBottom: "4px" }}>
            Purchase Price (per share)
          </label>

          <input
            type="number"
            step="0.01"
            placeholder="e.g. 150.00"
            value={avgPrice}
            onChange={(e) => setAvgPrice(e.target.value)}
            required
          />

          <button
            type="button"
            onClick={() => setAvgPrice(currentPrice)}
            style={{ marginLeft: "10px" }}
          >
            Use Current Price
          </button>
        </div>

        <div style={{ marginBottom: "10px" }}>
          <label style={{ display: "block", marginBottom: "4px" }}>
            Current Market Price
          </label>

          <input
            type="number"
            step="0.01"
            placeholder="Auto-filled from lookup"
            value={currentPrice}
            onChange={(e) => setCurrentPrice(e.target.value)}
            required
          />
        </div>

        <button type="submit" disabled={loadingPrice}>
          Add
        </button>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}

export default AddPositionForm;