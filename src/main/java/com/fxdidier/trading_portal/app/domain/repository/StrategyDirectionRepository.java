package com.fxdidier.trading_portal.app.domain.repository;

import com.fxdidier.trading_portal.app.domain.entity.StrategyDirection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrategyDirectionRepository extends JpaRepository<StrategyDirection, Long> {

    boolean existsByCodeAndStrategyId(String code, Long strategyId);

    List<StrategyDirection> findByGlobalTrueOrOwnerIdOrderByNameAsc(Long ownerId);

    List<StrategyDirection> findByStrategyId(Long strategyId);
}
