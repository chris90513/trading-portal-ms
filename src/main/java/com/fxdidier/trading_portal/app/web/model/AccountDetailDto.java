package com.fxdidier.trading_portal.app.web.model;

import java.math.BigDecimal;

public record AccountDetailDto(
        Long id,
        String description,
        String accountNumber,
        BigDecimal initialBalance,

        Long userId,
        String userUsername,      // o email, depende de tu entidad User

        Long brokerId,
        String brokerName,

        Long accountTypeId,
        String accountTypeName,

        Long evaluationProgramId,
        String evaluationProgramName,

        Long evaluationStepId,
        String evaluationStepName,

        Long statusId,
        String statusName,

        Long previousAccountId,
        String previousAccountNumber
) {}
