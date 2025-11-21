package com.fxdidier.trading_portal.app.domain.repository;

import com.fxdidier.trading_portal.app.domain.entity.StrategyConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrategyConfirmationRepository extends JpaRepository<StrategyConfirmation, Long> {

    boolean existsByCodeAndStrategyId(String code, Long strategyId);

    List<StrategyConfirmation> findByGlobalTrueOrOwnerIdOrderByNameAsc(Long ownerId);

    List<StrategyConfirmation> findByStrategyId(Long strategyId);
}
