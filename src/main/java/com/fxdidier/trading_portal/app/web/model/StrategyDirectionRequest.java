package com.fxdidier.trading_portal.app.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StrategyDirectionRequest(

        @NotNull
        Long strategyId,

        @NotBlank
        String code,

        @NotBlank
        String name,

        Boolean global,   // sólo ADMIN puede poner true

        Long userId       // sólo ADMIN para asignar a otro user
) {}
