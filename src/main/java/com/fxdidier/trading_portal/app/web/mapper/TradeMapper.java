package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.Trade;
import com.fxdidier.trading_portal.app.web.model.TradeDto;
import com.fxdidier.trading_portal.app.web.model.TradeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    // Entity -> DTO
    @Mapping(target = "accountId",           source = "account.id")
    @Mapping(target = "accountDescription",  source = "account.description")

    @Mapping(target = "strategyId",          source = "strategy.id")
    @Mapping(target = "strategyName",        source = "strategy.name")

    @Mapping(target = "directionId",         source = "direction.id")
    @Mapping(target = "directionName",       source = "direction.name")

    @Mapping(target = "confirmationId",      source = "confirmation.id")
    @Mapping(target = "confirmationName",    source = "confirmation.name")
    TradeDto toDto(Trade trade);

    // Request -> Entity (CREATE)
    @Mapping(target = "id",            ignore = true)
    @Mapping(target = "account",       ignore = true)
    @Mapping(target = "strategy",      ignore = true)
    @Mapping(target = "direction",     ignore = true)
    @Mapping(target = "confirmation",  ignore = true)
    @Mapping(target = "user",          ignore = true)
    @Mapping(target = "links",         ignore = true)  // si luego los manejas aparte
    Trade toEntity(TradeRequest request);

    // Request -> Entity (UPDATE)
    @Mapping(target = "id",            ignore = true)
    @Mapping(target = "account",       ignore = true)
    @Mapping(target = "strategy",      ignore = true)
    @Mapping(target = "direction",     ignore = true)
    @Mapping(target = "confirmation",  ignore = true)
    @Mapping(target = "user",          ignore = true)
    @Mapping(target = "links",         ignore = true)
    void updateEntityFromRequest(TradeRequest request, @MappingTarget Trade trade);
}
