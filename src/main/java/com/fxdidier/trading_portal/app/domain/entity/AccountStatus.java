package com.fxdidier.trading_portal.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account_statuses")
@Getter
@Setter
public class AccountStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 'ACTIVE','PASSED','FAILED','RESET','ARCHIVED'
    @Column(nullable = false, unique = true, length = 30)
    private String code;

    // 'Activa','Pasada','Fallida','Reset','Archivada'
    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private boolean active = true;
}