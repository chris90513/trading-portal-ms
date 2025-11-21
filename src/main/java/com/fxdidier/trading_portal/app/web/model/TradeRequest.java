package com.fxdidier.trading_portal.app.web.model;

import com.fxdidier.trading_portal.util.enums.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

public record TradeRequest(

        @NotNull
        Long accountId,

        @NotNull
        Symbol symbol,

        @NotNull
        OffsetDateTime openedAt,

        @NotNull
        OffsetDateTime closedAt,

        @NotNull
        TradeSide side,

        @NotNull
        TradeOut status,

        Long strategyId,
        Long directionId,
        Long confirmationId,

        Bias dailyBias,
        Bias sessionBias,
        Boolean biasCorrect,

        Integer pipsSL,
        Integer pipsTP,
        Integer pipsResult,

        @NotNull
        BigDecimal entryPrice,

        @NotNull
        BigDecimal exitPrice,

        @NotNull
        BigDecimal positionSize,

        BigDecimal riskAmount,
        BigDecimal realRR,

        BigDecimal grossPnl,
        BigDecimal commission,
        BigDecimal swap,
        BigDecimal netPnl,
        BigDecimal maxRR,

        String brokerTicket,

        Set<Emotion> emotions,

        String coments,
        String details,
        String possibleEntries,
        String whatWentWell,
        String whatToImprove
) {}
