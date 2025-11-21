package com.fxdidier.trading_portal.app.web.model;

public record BrokerDto(
        Long id,
        String code,
        String name,
        String website,
        boolean active
) {}