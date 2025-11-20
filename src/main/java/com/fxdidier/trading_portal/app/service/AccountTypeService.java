package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.AccountType;
import com.fxdidier.trading_portal.app.domain.repository.AccountTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;

    @Transactional(readOnly = true)
    public AccountType getByIdOrThrow(Long id) {
        return accountTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountType not found with id: " + id));
    }
}
