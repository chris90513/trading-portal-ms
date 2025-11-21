package com.fxdidier.trading_portal.app.web.model;

import java.math.BigDecimal;

public record AccountSummaryDto(
        Long id,
        String description,
        String accountNumber,
        String brokerName,
        String accountTypeName,
        String statusName,
        BigDecimal initialBalance
) {}