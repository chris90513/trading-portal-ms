package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.EvaluationProgram;
import com.fxdidier.trading_portal.app.domain.repository.EvaluationProgramRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationProgramService {

    private final EvaluationProgramRepository repository;

    @Transactional(readOnly = true)
    public EvaluationProgram getByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EvaluationProgram not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<EvaluationProgram> getAll() {
        return repository.findAll(Sort.by("name").ascending());
    }
}
