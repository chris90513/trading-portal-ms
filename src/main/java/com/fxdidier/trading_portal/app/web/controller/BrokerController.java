package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.service.BrokerService;
import com.fxdidier.trading_portal.app.web.mapper.BrokerMapper;
import com.fxdidier.trading_portal.app.web.model.BrokerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/brokers")
@RequiredArgsConstructor
public class BrokerController {

    private final BrokerService service;
    private final BrokerMapper mapper;

    @GetMapping
    public ResponseEntity<List<BrokerDto>> findAll() {
        List<BrokerDto> dto = mapper.toDtoList(service.getAllActive());
        return ResponseEntity.ok(dto);
    }
}