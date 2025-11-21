package com.fxdidier.trading_portal.app.service;


import com.fxdidier.trading_portal.app.domain.entity.AccountStatus;
import com.fxdidier.trading_portal.app.domain.repository.AccountStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountStatusService {

    private final AccountStatusRepository repository;

    @Transactional(readOnly = true)
    public AccountStatus getByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountStatus not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<AccountStatus> getAllActive() {
        return repository.findByActiveTrueOrderByNameAsc();
    }
}