package com.fxdidier.trading_portal.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Alias para ti: "Neomaa 25K 2-step Fase 1", "Real Pepperstone", etc.
    @Column(length = 255)
    private String description;

    // Número / ID de cuenta en broker/prop firm
    @Column(name = "account_number", nullable = false, unique = true, length = 100)
    private String accountNumber;

    // Empresa / Broker: Pepperstone, Neomaa, WSF, Orion...
    @ManyToOne
    @JoinColumn(name = "broker_id", nullable = false)
    private Broker broker;

    // Tipo general: REAL / DEMO / PROP_FIRM
    @ManyToOne
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;

    // Programa de evaluación/fondeo: INSTANT, ONE_STEP, TWO_STEP...
    // null para REAL / DEMO
    @ManyToOne
    @JoinColumn(name = "evaluation_program_id")
    private EvaluationProgram evaluationProgram;

    // Step actual: STEP_1, STEP_2, FUNDED...
    // null para REAL / DEMO, o si no quieres modelar step para ciertos programas
    @ManyToOne
    @JoinColumn(name = "evaluation_step_id")
    private EvaluationStep evaluationStep;

    // Estado: ACTIVE, PASSED, FAILED, RESET, ARCHIVED
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private AccountStatus status;

    @Column(name = "initial_balance", nullable = false)
    private BigDecimal initialBalance = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}