package com.fxdidier.trading_portal.app.web.model;

public record StrategyDto(
        Long id,
        String code,
        String name,
        String description,
        boolean global,
        Long ownerId,
        String ownerUsername
) {}