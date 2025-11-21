package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.StrategyDirection;
import com.fxdidier.trading_portal.app.web.model.StrategyDirectionDto;
import com.fxdidier.trading_portal.app.web.model.StrategyDirectionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StrategyDirectionMapper {

    @Mapping(target = "strategyId",    source = "strategy.id")
    @Mapping(target = "ownerId",       source = "owner.id")
    @Mapping(target = "ownerUsername", source = "owner.username")
    StrategyDirectionDto toDto(StrategyDirection entity);

    List<StrategyDirectionDto> toDtoList(List<StrategyDirection> list);

    @Mapping(target = "id",       ignore = true)
    @Mapping(target = "strategy", ignore = true)
    @Mapping(target = "global",   ignore = true)
    @Mapping(target = "owner",    ignore = true)
    StrategyDirection toEntity(StrategyDirectionRequest request);

    @Mapping(target = "id",       ignore = true)
    @Mapping(target = "strategy", ignore = true)
    @Mapping(target = "global",   ignore = true)
    @Mapping(target = "owner",    ignore = true)
    void updateFromRequest(StrategyDirectionRequest request,
                           @MappingTarget StrategyDirection entity);
}