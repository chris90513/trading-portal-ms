package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.AccountType;
import com.fxdidier.trading_portal.app.domain.repository.AccountTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeService {

    private final AccountTypeRepository repository;

    @Transactional(readOnly = true)
    public AccountType getByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountType not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<AccountType> getAll() {
        // opcional: ordenado por nombre
        return repository.findAll(Sort.by("name").ascending());
    }
}
