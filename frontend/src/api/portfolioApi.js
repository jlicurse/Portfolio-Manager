const BASE_URL = "http://localhost:8080/api/portfolios";

export async function getPortfolios() {
  const response = await fetch(BASE_URL);

  if (!response.ok) {
    throw new Error("Failed to fetch portfolios");
  }

  return response.json();
}

export async function getPortfolioById(id) {
  const response = await fetch(`${BASE_URL}/${id}`);

  if (!response.ok) {
    throw new Error("Failed to fetch portfolio");
  }

  return response.json();
}

export async function createPortfolio(data) {
  const response = await fetch("http://localhost:8080/api/portfolios", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  });

  if (!response.ok) {
    throw new Error("Failed to create portfolio");
  }

  return response.json();
}

export async function addPosition(portfolioId, data) {
  const response = await fetch(
    `http://localhost:8080/api/portfolios/${portfolioId}/positions`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    }
  );

  if (!response.ok) {
    throw new Error("Failed to add position");
  }

  return response.json();
}

export async function getQuote(symbol) {
  const response = await fetch(`http://localhost:8080/api/quotes/${symbol}`);

  if (!response.ok) {
    throw new Error("Failed to fetch quote");
  }

  return response.json();
}