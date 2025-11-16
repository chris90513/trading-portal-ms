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
    @ManyToOne(optional = false)
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    // Código para lógica interna
    @Column(nullable = false, length = 50)
    private String code;   // "RANGE_1H", "RANGE_30M", "CONTINUATION"

    // Texto que ves en la UI / Notion
    @Column(nullable = false, length = 100)
    private String name;   // "Rango 1H", "Rango 30m", "Continuación"
}
