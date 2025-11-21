package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.Strategy;
import com.fxdidier.trading_portal.app.web.model.StrategyDto;
import com.fxdidier.trading_portal.app.web.model.StrategyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StrategyMapper {

    // Entity -> DTO
    @Mapping(target = "ownerId",       source = "owner.id")
    @Mapping(target = "ownerUsername", source = "owner.username")
    StrategyDto toDto(Strategy strategy);

    List<StrategyDto> toDtoList(List<Strategy> strategies);

    // Request -> Entity (CREATE)
    @Mapping(target = "id",    ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "global", ignore = true) // se decide en el service
    Strategy toEntity(StrategyRequest request);

    // Request -> Entity (UPDATE)
    @Mapping(target = "id",    ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "global", ignore = true)
    void updateEntityFromRequest(StrategyRequest request, @MappingTarget Strategy strategy);
}