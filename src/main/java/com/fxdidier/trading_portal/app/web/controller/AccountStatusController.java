package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.service.AccountStatusService;
import com.fxdidier.trading_portal.app.web.mapper.AccountStatusMapper;
import com.fxdidier.trading_portal.app.web.model.AccountStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-statuses")
@RequiredArgsConstructor
public class AccountStatusController {

    private final AccountStatusService service;
    private final AccountStatusMapper mapper;

    @GetMapping
    public ResponseEntity<List<AccountStatusDto>> findAll() {
        return ResponseEntity.ok(
                mapper.toDtoList(service.getAllActive())
        );
    }
}