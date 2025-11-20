package com.fxdidier.trading_portal.app.web.model;

import java.math.BigDecimal;

public record AccountRequest(
        String description,
        String accountNumber,
        Long brokerId,
        Long accountTypeId,
        Long evaluationProgramId,   // nullable
        Long evaluationStepId,      // nullable
        Long statusId,
        BigDecimal initialBalance,
        Long userId,                // Solo ADMIN lo usa. Para USER se ignora.
        Long previousAccountId      // nullable
) {}