package com.fxdidier.trading_portal.app.domain.repository;

import com.fxdidier.trading_portal.app.domain.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {

    boolean existsByCode(String code);

    // Estrategias globales (para todos)
    List<Strategy> findByGlobalTrueOrderByNameAsc();

    // Estrategias propias de un usuario
    List<Strategy> findByOwnerIdOrderByNameAsc(Long ownerId);

    // Globales + propias
    List<Strategy> findByGlobalTrueOrOwnerIdOrderByNameAsc(Long ownerId);
}
