package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.domain.entity.EvaluationProgram;
import com.fxdidier.trading_portal.app.service.EvaluationProgramService;
import com.fxdidier.trading_portal.app.web.mapper.EvaluationProgramMapper;
import com.fxdidier.trading_portal.app.web.model.EvaluationProgramDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/evaluation-programs")
@RequiredArgsConstructor
public class EvaluationProgramController {
    private final EvaluationProgramService service;
    private final EvaluationProgramMapper mapper;

    @GetMapping
    public ResponseEntity<List<EvaluationProgramDto>> getAll() {
        return ResponseEntity.ok(
                mapper.toDtoList(service.getAll())
        );
    }
}
