package com.fxdidier.trading_portal.app.service;


import com.fxdidier.trading_portal.app.domain.entity.AccountStatus;
import com.fxdidier.trading_portal.app.domain.repository.AccountStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountStatusService {

    private final AccountStatusRepository accountStatusRepository;

    @Transactional(readOnly = true)
    public AccountStatus getByIdOrThrow(Long id) {
        return accountStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountStatus not found with id: " + id));
    }
}