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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = true)
    private String description;

    // true = confirmación global (para todos)
    @Column(name = "is_global", nullable = false)
    private boolean global = false;

    // propietario cuando no es global
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;
}
