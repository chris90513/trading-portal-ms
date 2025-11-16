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

    @Column(nullable = false, unique = true)
    private String code;    // "NY_POWER", "MASTER_PROFITS"

    @Column(nullable = false)
    private String name;    // "NY Power Setup", "Master Profits"

    private String description;
}