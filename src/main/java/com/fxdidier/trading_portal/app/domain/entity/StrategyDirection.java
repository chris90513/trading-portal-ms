package com.fxdidier.trading_portal.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "strategy_directions")
@Getter
@Setter
public class StrategyDirection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Estrategia a la que pertenece
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    // Texto que ves en la UI / Notion
    @Column(nullable = false, length = 100)
    private String name;   // "Rango 1H", "Rango 30m", "Continuación"

    // ---- NUEVO ----

    // true = visible para todos
    @Column(name = "is_global", nullable = false)
    private boolean global = false;

    // dueño cuando no es global
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;
}
