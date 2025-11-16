package com.fxdidier.trading_portal.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "evaluation_steps")
@Getter
@Setter
public class EvaluationStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 'STEP_1', 'STEP_2', 'STEP_3', 'FUNDED'
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    // 'Step 1', 'Step 2', 'Step 3', 'Funded'
    @Column(nullable = false, length = 100)
    private String name;

    // para ordenar (1,2,3...) si lo necesitas
    @Column(name = "step_order")
    private Short stepOrder;

    // true si esta etapa implica que ya es cuenta fondeada
    @Column(name = "funded_stage", nullable = false)
    private boolean fundedStage = false;

    @Column(nullable = false)
    private boolean active = true;
}