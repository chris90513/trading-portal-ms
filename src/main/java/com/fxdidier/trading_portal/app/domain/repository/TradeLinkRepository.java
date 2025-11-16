package com.fxdidier.trading_portal.app.domain.repository;

import com.fxdidier.trading_portal.app.domain.entity.TradeLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeLinkRepository extends JpaRepository<TradeLink, Long> {
}
