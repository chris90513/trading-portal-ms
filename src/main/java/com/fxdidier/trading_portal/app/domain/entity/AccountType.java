package com.fxdidier.trading_portal.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account_types")
@Getter
@Setter
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 'REAL', 'DEMO', 'PROP_FIRM'
    @Column(nullable = false, unique = true, length = 30)
    private String code;

    // 'Cuenta Real', 'Cuenta Demo', 'Cuenta de Prop Firm'
    @Column(nullable = false, length = 100)
    private String name;

    private String description;
}