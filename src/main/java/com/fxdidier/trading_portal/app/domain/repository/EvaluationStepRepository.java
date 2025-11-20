package com.fxdidier.trading_portal.app.domain.repository;

import com.fxdidier.trading_portal.app.domain.entity.EvaluationStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationStepRepository extends JpaRepository<EvaluationStep, Long>
{
}
