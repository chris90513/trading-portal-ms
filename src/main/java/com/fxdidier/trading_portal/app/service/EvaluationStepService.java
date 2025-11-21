package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.EvaluationStep;
import com.fxdidier.trading_portal.app.domain.repository.EvaluationStepRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationStepService {

    private final EvaluationStepRepository repository;

    @Transactional(readOnly = true)
    public EvaluationStep getByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EvaluationStep not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<EvaluationStep> getAllActive() {
        return repository.findByActiveTrueOrderByStepOrderAscNameAsc();
    }
}