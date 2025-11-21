package com.fxdidier.trading_portal.app.web.model;

import com.fxdidier.trading_portal.util.enums.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

public record TradeDto(
        Long id,

        Long accountId,
        String accountDescription,

        Symbol symbol,
        OffsetDateTime openedAt,
        OffsetDateTime closedAt,
        TradeSide side,
        TradeOut status,

        Long strategyId,
        String strategyName,

        Long directionId,
        String directionName,

        Long confirmationId,
        String confirmationName,

        Bias dailyBias,
        Bias sessionBias,
        Boolean biasCorrect,

        Integer pipsSL,
        Integer pipsTP,
        Integer pipsResult,

        BigDecimal entryPrice,
        BigDecimal exitPrice,
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
