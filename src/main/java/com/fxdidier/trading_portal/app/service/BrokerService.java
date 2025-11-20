package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.Broker;
import com.fxdidier.trading_portal.app.domain.repository.BrokerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrokerService {

    private final BrokerRepository brokerRepository;

    @Transactional(readOnly = true)
    public Broker getByIdOrThrow(Long id) {
        return brokerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Broker not found with id: " + id));
    }
}
