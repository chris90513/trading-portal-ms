package com.fxdidier.trading_portal.app.web.model;

public record StrategyDirectionDto(
        Long id,
        Long strategyId,
        String name,
        boolean global,
        Long ownerId,
        String ownerUsername
) {}
