package com.fxdidier.trading_portal.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "brokers")
@Getter
@Setter
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 'PEPPERSTONE', 'NEOMAA', 'WSF', 'ORION', etc.
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    // Nombre descriptivo
    @Column(nullable = false, length = 100)
    private String name;

    private String website;

    @Column(nullable = false)
    private boolean active = true;
}