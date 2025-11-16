package com.fxdidier.trading_portal.app.domain.entity;

import com.fxdidier.trading_portal.util.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "trades")
@Getter
@Setter
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la cuenta
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Symbol symbol;

    // Fecha y hora de apertura
    @Column(name = "opened_at", nullable = false)
    private OffsetDateTime openedAt;

    // Fecha y hora de cierre
    @Column(name = "closed_at", nullable = false)
    private OffsetDateTime closedAt;

    // Long o Short
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TradeSide side;

    // Estado: TP, SL, BE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TradeOut status;

    // Estrategia (relación)
    @ManyToOne
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private StrategyDirection direction;

    @ManyToOne
    @JoinColumn(name = "confirmation_id")
    private StrategyConfirmation confirmation;

    @Enumerated(EnumType.STRING)
    private Bias dailyBias;

    @Enumerated(EnumType.STRING)
    private Bias sessionBias;

    @Column(name = "bias_correct")
    private Boolean biasCorrect;

    // Pips SL y TP
    @Column(name = "pips_sl")
    private Integer pipsSL;

    @Column(name = "pips_tp")
    private Integer pipsTP;

    // Ganancia bruta ($)
    @Column(name = "gross_pnl", precision = 10, scale = 2)
    private BigDecimal grossPnl;

    // Comisión ($)
    @Column(name = "commission", precision = 10, scale = 2)
    private BigDecimal commission;

    // Ganancia neta ($)
    @Column(name = "net_pnl", precision = 10, scale = 2)
    private BigDecimal netPnl;

    // Max RR alcanzado
    @Column(name = "max_rr", precision = 5, scale = 2)
    private BigDecimal maxRR;

    @OneToMany(
            mappedBy = "trade",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TradeLink> links = new ArrayList<>();

    @ElementCollection(targetClass = Emotion.class)
    @CollectionTable(
            name = "trade_emotions",
            joinColumns = @JoinColumn(name = "trade_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "emotion", nullable = false, length = 20)
    private Set<Emotion> emotions = new HashSet<>();

    @Column(columnDefinition = "text")
    private String coments;

    @Column(columnDefinition = "text")
    private String details;

    @Column(name = "possible_entries", columnDefinition = "text")
    private String possibleEntries;

    @Column(name = "what_went_well", columnDefinition = "text")
    private String whatWentWell;

    @Column(name = "what_to_improve", columnDefinition = "text")
    private String whatToImprove;
}
