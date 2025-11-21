package com.fxdidier.trading_portal.app.domain.repository;

import com.fxdidier.trading_portal.app.domain.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    Page<Trade> findByUserId(Long userId, Pageable pageable);
}
