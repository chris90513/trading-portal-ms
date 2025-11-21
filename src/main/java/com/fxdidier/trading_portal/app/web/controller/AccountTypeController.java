package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.service.AccountTypeService;
import com.fxdidier.trading_portal.app.web.mapper.AccountTypeMapper;
import com.fxdidier.trading_portal.app.web.model.AccountTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-types")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeService service;
    private final AccountTypeMapper mapper;

    @GetMapping
    public ResponseEntity<List<AccountTypeDto>> findAll() {
        return ResponseEntity.ok(
                mapper.toDtoList(service.getAll())
        );
    }
}