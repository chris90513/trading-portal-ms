package com.fxdidier.trading_portal.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "evaluation_programs")
@Getter
@Setter
public class EvaluationProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 'INSTANT', 'ONE_STEP', 'TWO_STEP', 'THREE_STEP'
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    // 'Instant Funding', '1 Step Evaluation', '2 Step Evaluation', etc.
    @Column(nullable = false, length = 150)
    private String name;

    // número total de steps/fases de ese programa (1,2,3...)
    @Column(name = "total_steps", nullable = false)
    private Short totalSteps;

    @Column(columnDefinition = "text")
    private String description;
}
