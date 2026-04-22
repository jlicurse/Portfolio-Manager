import { useState } from "react";
import { createPortfolio } from "../api/portfolioApi";

function CreatePortfolioForm({ onCreated }) {
  const [name, setName] = useState("");
  const [owner, setOwner] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();

    try {
      setError("");

      await createPortfolio({ name, owner });

      setName("");
      setOwner("");

      onCreated(); // refresh list
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ marginBottom: "20px" }}>
      <h3>Create Portfolio</h3>

      <form onSubmit={handleSubmit}>
        <input
          placeholder="Portfolio Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />

        <input
          placeholder="Owner"
          value={owner}
          onChange={(e) => setOwner(e.target.value)}
          required
        />

        <button type="submit">Create</button>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}

export default CreatePortfolioForm;