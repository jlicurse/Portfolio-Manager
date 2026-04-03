package com.portfolio.portfolio_manager.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioSnapshotRepository extends JpaRepository<PortfolioSnapshotEntity, UUID> {
    List<PortfolioSnapshotEntity> findByPortfolioIdOrderByTimestampAsc(UUID portfolioId);
}
