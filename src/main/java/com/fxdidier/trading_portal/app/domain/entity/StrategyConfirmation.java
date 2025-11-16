package com.fxdidier.trading_portal.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "strategy_confirmations")
@Getter
@Setter
public class StrategyConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Estrategia a la que pertenece
    @ManyToOne(optional = false)
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @Column(nullable = false, length = 50)
    private String code;   // "LIQUIDITY_TAKE_5M", "ENGULFING_OB_5M"

    @Column(nullable = false, length = 150)
    private String name;   // "Toma de Liquidez 5M", "Envolvente 5M en OB"
}
