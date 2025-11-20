package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.EvaluationProgram;
import com.fxdidier.trading_portal.app.domain.repository.EvaluationProgramRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EvaluationProgramService {

    private final EvaluationProgramRepository evaluationProgramRepository;

    @Transactional(readOnly = true)
    public EvaluationProgram getByIdOrThrow(Long id) {
        return evaluationProgramRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EvaluationProgram not found with id: " + id));
    }
}
