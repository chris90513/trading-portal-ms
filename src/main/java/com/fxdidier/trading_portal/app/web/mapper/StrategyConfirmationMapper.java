package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.StrategyConfirmation;
import com.fxdidier.trading_portal.app.web.model.StrategyConfirmationDto;
import com.fxdidier.trading_portal.app.web.model.StrategyConfirmationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StrategyConfirmationMapper {

    @Mapping(target = "strategyId", source = "strategy.id")
    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "ownerUsername", source = "owner.username")
    StrategyConfirmationDto toDto(StrategyConfirmation entity);

    List<StrategyConfirmationDto> toDtoList(List<StrategyConfirmation> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "strategy", ignore = true)
    @Mapping(target = "global", ignore = true)
    @Mapping(target = "owner", ignore = true)
    StrategyConfirmation toEntity(StrategyConfirmationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "strategy", ignore = true)
    @Mapping(target = "global", ignore = true)
    @Mapping(target = "owner", ignore = true)
    void updateFromRequest(StrategyConfirmationRequest request,
                           @MappingTarget StrategyConfirmation entity);
}
