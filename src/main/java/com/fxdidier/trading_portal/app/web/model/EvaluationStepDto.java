package com.fxdidier.trading_portal.app.web.model;

public record EvaluationStepDto(
        Long id,
        String code,
        String name,
        Short stepOrder,
        boolean fundedStage,
        boolean active
) {}