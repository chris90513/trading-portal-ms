package com.fxdidier.trading_portal.app.web.model;

public record AccountTypeDto(
        Long id,
        String code,
        String name,
        String description
) {}