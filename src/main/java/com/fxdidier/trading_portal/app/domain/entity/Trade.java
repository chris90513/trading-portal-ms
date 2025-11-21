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

    // --------------------- RELACIONES ---------------------

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private StrategyDirection direction;

    @ManyToOne
    @JoinColumn(name = "confirmation_id")
    private StrategyConfirmation confirmation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --------------------- DATOS DEL TRADE ---------------------

    // Ticket único del broker / prop firm
    @Column(name = "broker_ticket", length = 50)
    private String brokerTicket;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Symbol symbol;

    @Column(name = "opened_at", nullable = false)
    private OffsetDateTime openedAt;

    @Column(name = "closed_at", nullable = false)
    private OffsetDateTime closedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TradeSide side;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TradeOut status;

    @Enumerated(EnumType.STRING)
    private Bias dailyBias;

    @Enumerated(EnumType.STRING)
    private Bias sessionBias;

    @Column(name = "bias_correct")
    private Boolean biasCorrect;

    // --------------------- PRECIOS ---------------------

    @Column(name = "entry_price", precision = 15, scale = 5, nullable = false)
    private BigDecimal entryPrice;

    @Column(name = "exit_price", precision = 15, scale = 5, nullable = false)
    private BigDecimal exitPrice;

    @Column(name = "position_size", precision = 10, scale = 2, nullable = false)
    private BigDecimal positionSize;

    // --------------------- METRICAS ---------------------

    @Column(name = "pips_sl")
    private Integer pipsSL;

    @Column(name = "pips_tp")
    private Integer pipsTP;

    // Nuevo → pips reales ganados/perdidos
    @Column(name = "pips_result")
    private Integer pipsResult;

    // Nuevo → cuánto se arriesgó ($)
    @Column(name = "risk_amount", precision = 10, scale = 2)
    private BigDecimal riskAmount;

    // Nuevo → R real final (netPnl / riskAmount)
    @Column(name = "real_rr", precision = 5, scale = 2)
    private BigDecimal realRR;

    @Column(name = "gross_pnl", precision = 10, scale = 2)
    private BigDecimal grossPnl;

    @Column(name = "commission", precision = 10, scale = 2)
    private BigDecimal commission;

    // Nuevo → coste de swap
    @Column(name = "swap", precision = 10, scale = 2)
    private BigDecimal swap;

    @Column(name = "net_pnl", precision = 10, scale = 2)
    private BigDecimal netPnl;

    // max RR alcanzado durante la operación
    @Column(name = "max_rr", precision = 5, scale = 2)
    private BigDecimal maxRR;

    // --------------------- OTROS ---------------------

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
