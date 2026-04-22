import { useEffect, useState } from "react";
import {
  getPortfolios,
  getPortfolioById
} from "./api/portfolioApi";
import PortfolioList from "./components/PortfolioList";
import PortfolioDetail from "./components/PortfolioDetail";
import CreatePortfolioForm from "./components/CreatePortfolioForm";
import "./index.css";

function App() {
  const [portfolios, setPortfolios] = useState([]);
  const [selectedPortfolio, setSelectedPortfolio] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    loadPortfolios();
  }, []);

  async function loadPortfolios() {
    try {
      setError("");
      const data = await getPortfolios();
      setPortfolios(data);
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleSelectPortfolio(id) {
    try {
      setError("");
      const data = await getPortfolioById(id);
      setSelectedPortfolio(data);
    } catch (err) {
      setError(err.message);
    }
  }
  
  async function reloadSelectedPortfolio(id) {
    try {
      setError("");
      const data = await getPortfolioById(id);
      setSelectedPortfolio(data);
      await loadPortfolios();
    } catch (err) {
      setError(err.message);
    }
}

  return (
    <div className="app">
      <header className="app-header">
        <h1>Portfolio Manager</h1>
      </header>

      {error && <p className="error-message">{error}</p>}

      <main className="app-layout">
        <div>
          <CreatePortfolioForm onCreated={loadPortfolios} />

          <PortfolioList
            portfolios={portfolios}
            onSelectPortfolio={handleSelectPortfolio}
            selectedPortfolioId={selectedPortfolio?.id}
          />
        </div>

        <PortfolioDetail portfolio={selectedPortfolio}  
        onPositionAdded={() => reloadSelectedPortfolio(selectedPortfolio.id)} />

      </main>
    </div>
  );
}

export default App;