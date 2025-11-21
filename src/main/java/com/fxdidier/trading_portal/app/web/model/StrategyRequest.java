package com.fxdidier.trading_portal.app.web.model;

import jakarta.validation.constraints.NotBlank;

public record StrategyRequest(
        @NotBlank
        String code,
        @NotBlank
        String name,
        String description,
        Boolean global,   // null = se decide según rol
        Long userId       // sólo lo usa ADMIN para crear para otro user
) {}