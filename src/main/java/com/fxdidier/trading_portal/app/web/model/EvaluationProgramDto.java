package com.fxdidier.trading_portal.app.web.model;

public record EvaluationProgramDto(
        Long id,
        String code,
        String name,
        Short totalSteps,
        String description
) {}