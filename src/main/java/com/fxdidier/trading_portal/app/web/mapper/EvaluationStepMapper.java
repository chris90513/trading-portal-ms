package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.EvaluationStep;
import com.fxdidier.trading_portal.app.web.model.EvaluationStepDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvaluationStepMapper {

    EvaluationStepDto toDto(EvaluationStep step);

    List<EvaluationStepDto> toDtoList(List<EvaluationStep> steps);
}