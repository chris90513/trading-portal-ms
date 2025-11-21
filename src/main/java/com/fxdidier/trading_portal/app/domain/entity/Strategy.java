package com.fxdidier.trading_portal.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "strategies")
@Getter
@Setter
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // "NY_POWER", "MASTER_PROFITS"
    @Column(nullable = false, unique = true)
    private String code;

    // "NY Power Setup", "Master Profits"
    @Column(nullable = false)
    private String name;

    private String description;

    // true = estrategia global/publica (visible para todos los usuarios)
    @Column(name = "is_global", nullable = false)
    private boolean global = false;

    // dueño de la estrategia (para estrategias de usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;
}