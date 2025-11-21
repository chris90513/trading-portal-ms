package com.fxdidier.trading_portal.app.web.mapper;

import com.fxdidier.trading_portal.app.domain.entity.EvaluationProgram;
import com.fxdidier.trading_portal.app.web.model.EvaluationProgramDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvaluationProgramMapper {

    EvaluationProgramDto toDto(EvaluationProgram program);

    List<EvaluationProgramDto> toDtoList(List<EvaluationProgram> programs);
}