package com.fxdidier.trading_portal.app.web.controller;
import com.fxdidier.trading_portal.app.service.EvaluationStepService;
import com.fxdidier.trading_portal.app.web.mapper.EvaluationStepMapper;
import com.fxdidier.trading_portal.app.web.model.EvaluationStepDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation-steps")
@RequiredArgsConstructor
public class EvaluationStepController {
    private final EvaluationStepService service;
    private final EvaluationStepMapper mapper;

    @GetMapping
    public ResponseEntity<List<EvaluationStepDto>> findAll() {
        return ResponseEntity.ok(
                mapper.toDtoList(service.getAllActive())
        );
    }
}