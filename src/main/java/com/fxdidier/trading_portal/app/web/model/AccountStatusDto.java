package com.fxdidier.trading_portal.app.web.model;


public record AccountStatusDto(
        Long id,
        String code,
        String name,
        String description,
        boolean active
) {}