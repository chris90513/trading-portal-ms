package com.fxdidier.trading_portal.app.web.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StrategyConfirmationRequest(

        @NotNull
        Long strategyId,

        @NotBlank
        String code,

        @NotBlank
        String name,

        Boolean global, // sólo admin puede pasarlo true

        Long userId     // solo admin
) {}