package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.EvaluationStep;
import com.fxdidier.trading_portal.app.domain.repository.EvaluationStepRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EvaluationStepService {

    private final EvaluationStepRepository evaluationStepRepository;

    @Transactional(readOnly = true)
    public EvaluationStep getByIdOrThrow(Long id) {
        return evaluationStepRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EvaluationStep not found with id: " + id));
    }
}